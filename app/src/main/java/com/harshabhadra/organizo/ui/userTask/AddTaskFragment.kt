package com.harshabhadra.organizo.ui.userTask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener,
    View.OnClickListener {

    private lateinit var addTaskBinding: FragmentAddTaskBinding
    private var isStart:Boolean = false
    private lateinit var addTaskViewModel: AddTaskViewModel
    private var startTimeButtonClicked = false
    private var startDateButtonClicked = false

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

        if (!isStart){
            addTaskBinding.addTaskBody.endTimeCard.visibility = View.VISIBLE
            addTaskBinding.chooseFab.setImageDrawable(context?.let {getDrawable(it, R.drawable.ic_save_black_24dp) })
            addTaskBinding.addTaskBody.startTimeChangeButton.visibility = View.VISIBLE
            addTaskBinding.addTaskBody.startDateChangeButton.visibility = View.VISIBLE
        }

        //Set on Click listener to the change button
        addTaskBinding.addTaskBody.startTimeChangeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.startDateChangeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.changeEndTimeButton.setOnClickListener(this)
        addTaskBinding.addTaskBody.endDateChangeButton.setOnClickListener(this)
        return addTaskBinding.root
    }

    override fun onStart() {
        super.onStart()
        addTaskBinding.addTaskViewModel = addTaskViewModel
        addTaskViewModel.getTime()
        addTaskViewModel.getDate()
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {

        if (startTimeButtonClicked){
            addTaskBinding.addTaskBody.startTaskTimeTv.text = "$hourOfDay : $minute"
        }else{
            addTaskBinding.addTaskBody.endTaskTimeTv.text = "$hourOfDay : $minute"
        }
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        if (startDateButtonClicked){
            addTaskBinding.addTaskBody.startTaskDateTv.text = changeDateFormat(dayOfMonth,monthOfYear,year)
        }else{
            addTaskBinding.addTaskBody.endTaskDateTv.text = changeDateFormat(dayOfMonth,monthOfYear,year)
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
        }
    }

    //Open Date picker Dialog
    fun openDatePickerDialog(){
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
    fun openTimePickerDialog(){
        val now = Calendar.getInstance()
        val dpd =
            TimePickerDialog.newInstance(
                this, now[Calendar.HOUR], now[Calendar.MINUTE], false
            )
        dpd.show(activity?.supportFragmentManager!!, "Datepickerdialog")
    }

    //Format Date
    fun changeDateFormat(day:Int, month:Int, year:Int):String{
        val format = SimpleDateFormat("E, dd MMM yyyy", Locale.getDefault())
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse("$day/$month/$year")
        return format.format(date)
    }
}
