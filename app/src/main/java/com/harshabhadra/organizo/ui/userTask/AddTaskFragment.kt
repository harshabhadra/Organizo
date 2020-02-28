package com.harshabhadra.organizo.ui.userTask


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.database.Category
import com.harshabhadra.organizo.database.Task
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,
    View.OnClickListener{

    private lateinit var addTaskBinding: FragmentAddTaskBinding
    private var isStart:Boolean = false
    private lateinit var addTaskViewModel: AddTaskViewModel
    private var startTimeButtonClicked = false
    private var startDateButtonClicked = false
    private var isChecked: Boolean? = null
    private lateinit var categoryAdapter: CategoryAdapter
    private var canInsertCat:Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        //Initializing ViewModel class
        val addTaskViewModelFactory = AddTaskViewModelFactory(application)
        addTaskViewModel = ViewModelProvider(this,addTaskViewModelFactory).get(AddTaskViewModel::class.java)

        //Check what to do Start or save task
        isStart = arguments?.getBoolean("isStart")!!

        //Check if the add end card checkbox is checked or not
        isChecked = addTaskBinding.addTaskBody.checkedTextView.isChecked

        if (!isStart){
            addTaskBinding.addTaskBody.checkedTextView.visibility=View.VISIBLE
            addTaskBinding.chooseFab.setImageDrawable(context?.let {getDrawable(it, R.drawable.ic_save_black_24dp) })
            addTaskBinding.addTaskBody.startTimeChangeButton.visibility = View.VISIBLE
            addTaskBinding.addTaskBody.startDateChangeButton.visibility = View.VISIBLE
        }

        //Set on Click listener to the change buttons
        addTaskBinding.addTaskBody.startTimeChangeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.startDateChangeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.changeEndTimeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.endDateChangeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.checkedTextView.setOnClickListener(this)
        addTaskBinding.addCategoryTv.setOnClickListener(this)

        //Initializing category adapter
         categoryAdapter = CategoryAdapter()
        //Setting up the recyclerView
        addTaskBinding.categoryNameRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        addTaskBinding.categoryNameRecycler.adapter = categoryAdapter

        categoryAdapter.onItemClick = {
            pos, view ->
            val category = categoryAdapter.getCategoryItem(pos)
            addTaskBinding.addCategoryTv.text = category.categoryName
        }
        //Observe category list
        addTaskViewModel.allCategories.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            it?.let {
                categoryAdapter.submitList(it)
                addTaskViewModel.noOfCategories(it.size)
            }})

        //Observe if the task is inserted
        addTaskViewModel.taskInserted.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
           if(it == true) {
               this.findNavController()
                   .navigate(AddTaskFragmentDirections.actionAddTaskFragmentToHomeFragment())
               addTaskViewModel.taskInserTionCompleted()
           }
        })

        //Observe if user can add categories
        addTaskViewModel.canInsert.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            canInsertCat = it
        })

        //Saving a task to the database
        addTaskBinding.chooseFab.setOnClickListener(this)

        //Adding items to the category list if the list is empty
        return addTaskBinding.root
    }

    override fun onStart() {
        super.onStart()
        addTaskBinding.addTaskViewModel = addTaskViewModel
        addTaskViewModel.getDate()
        addTaskViewModel.getTime()
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {

        if (startTimeButtonClicked){
            addTaskBinding.addTaskBody.startTaskTimeTv.text = addTaskViewModel.changeTimeFormat(hourOfDay,minute)
        }else{
            addTaskBinding.addTaskBody.endTaskTimeTv.text = addTaskViewModel.changeTimeFormat(hourOfDay,minute)
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        if (startDateButtonClicked) {
            addTaskBinding.addTaskBody.startTaskDateTv.text =
                addTaskViewModel.changeDateFormat(dayOfMonth, monthOfYear, year)
        }else{
            addTaskBinding.addTaskBody.endTaskDateTv.text =
                addTaskViewModel.changeDateFormat(dayOfMonth,monthOfYear,year)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            addTaskBinding.addTaskBody.startTimeChangeButton -> {
                startTimeButtonClicked = true
                openTimePickerDialog()
            }
            addTaskBinding.addTaskBody.changeEndTimeButton -> {
                startTimeButtonClicked = false
                openTimePickerDialog()
            }
            addTaskBinding.addTaskBody.startDateChangeButton -> {
                startDateButtonClicked = true
                openDatePickerDialog()
            }
            addTaskBinding.addTaskBody.endDateChangeButton -> {
                startDateButtonClicked = false
                openDatePickerDialog()
            }
            addTaskBinding.addTaskBody.checkedTextView -> {
                if (!isChecked!!) {
                    addTaskBinding.addTaskBody.checkedTextView.checkMarkDrawable =
                        ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_check_box_blue_24dp
                        )
                    addTaskBinding.addTaskBody.endTimeCard.visibility = View.VISIBLE
                    isChecked = true
                }else{
                    addTaskBinding.addTaskBody.checkedTextView.checkMarkDrawable =
                        ContextCompat.getDrawable(
                            requireContext(), R.drawable.ic_check_box_outline_blank_blue_24dp
                        )
                    addTaskBinding.addTaskBody.endTimeCard.visibility = View.INVISIBLE
                    isChecked = false
                }
            }
            addTaskBinding.addCategoryTv ->{
                if (canInsertCat){
                openAddCategoryDialgo()
                }else{
                    Toast.makeText(context,"You cannot add more Categories", Toast.LENGTH_SHORT).show()
                }
            }
            addTaskBinding.chooseFab ->{
                if(!isStart){
                    var taskName = addTaskBinding.taskNameTv.text.toString()
                    var categoryName = addTaskBinding.addCategoryTv.text.toString()
                    var taskDes = addTaskBinding.addTaskBody.taskDesEditText.text.toString()
                    var startTime = addTaskBinding.addTaskBody.startTaskTimeTv.text.toString()
                    var startDate = addTaskBinding.addTaskBody.startTaskDateTv.text.toString()

                    val task = Task(taskName =taskName, taskCategory = categoryName, taskDescription = taskDes,
                        startTime = startTime,startDate = startDate)
                    addTaskViewModel.insertTask(task)
                }
            }
        }
    }

    //Open Date picker Dialog
    private fun openDatePickerDialog(){
        val now = Calendar.getInstance()
        val dpd =
            DatePickerDialog.newInstance(
                this,
                now[Calendar.YEAR],  // Initial year selection
                now[Calendar.MONTH],  // Initial month selection
                now[Calendar.DAY_OF_MONTH] // Inital day selection
            )
        dpd.show(activity?.supportFragmentManager!!, "Datepickerdialog")
    }

    //Open time picker Dialgo
    private fun openTimePickerDialog(){
        val now = Calendar.getInstance()
        val dpd =
            TimePickerDialog.newInstance(
                this, now[Calendar.HOUR], now[Calendar.MINUTE], false
            )
        dpd.show(activity?.supportFragmentManager!!, "Datepickerdialog")
    }

    //Open add Category dialog
    private fun openAddCategoryDialgo(){
        val layout: View = LayoutInflater.from(context).inflate(R.layout.add_category_dialog_layout, null)
        val categoryNameET:EditText = layout.findViewById(R.id.dialog_category_name)
        val createButton:Button = layout.findViewById(R.id.create_category_button)
        val cancelButton:Button = layout.findViewById(R.id.close_cat_dialog_button)

        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setView(layout)
        builder?.setCancelable(false)
        val dialog = builder?.create()
        dialog?.show()

        createButton.setOnClickListener {
            val name = categoryNameET.text.toString()
            if(name.isNotEmpty()){
                val category = Category(name)
                addTaskViewModel.insertCategory(category)
                dialog?.dismiss()
            }else{
                Toast.makeText(context,"Category must have a name", Toast.LENGTH_SHORT).show()
            }
        }
        cancelButton.setOnClickListener { dialog?.dismiss() }
    }

}
