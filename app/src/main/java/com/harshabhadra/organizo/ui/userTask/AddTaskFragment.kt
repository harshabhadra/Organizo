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
import java.util.*


/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    private lateinit var addTaskBinding: FragmentAddTaskBinding
    private var isStart:Boolean = false
    private lateinit var addTaskViewModel: AddTaskViewModel

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
        }

        //Set on Click listener to the change button
        addTaskBinding.addTaskBody.startTimeChangeButton.setOnClickListener {
            val now = Calendar.getInstance()
            val dpd =
                DatePickerDialog.newInstance(
                    this,
                    now[Calendar.YEAR],  // Initial year selection
                    now[Calendar.MONTH],  // Initial month selection
                    now[Calendar.DAY_OF_MONTH] // Inital day selection
                )
            TimePickerDialog.newInstance(
                this,now[Calendar.HOUR],now[Calendar.MINUTE],false
            )
            dpd.show(activity?.supportFragmentManager!!, "Datepickerdialog")
        }
        return addTaskBinding.root
    }

    override fun onStart() {
        super.onStart()
        addTaskBinding.addTaskViewModel = addTaskViewModel
        addTaskViewModel.getTime()
        addTaskViewModel.getDate()
    }

    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {


    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int) {


    }


}
