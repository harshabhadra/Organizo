package com.harshabhadra.organizo.ui.userTask


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates


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
    private var isChecked: Boolean? = null

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


}
