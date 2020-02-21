package com.harshabhadra.organizo.ui.userTask


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.harshabhadra.organizo.R

import com.harshabhadra.organizo.Viewanimation.initView
import com.harshabhadra.organizo.Viewanimation.rotateFab
import com.harshabhadra.organizo.Viewanimation.showIn
import com.harshabhadra.organizo.Viewanimation.showOut
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding
import kotlinx.android.synthetic.main.add_task_body.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {

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
            addTaskBinding.chooseFab.setImageDrawable(context?.let {getDrawable(it,R.drawable.ic_save_black_24dp) })
            addTaskBinding.addTaskBody.startTimeChangeButton.visibility = View.VISIBLE
        }
        return addTaskBinding.root
    }

    override fun onStart() {
        super.onStart()
        addTaskBinding.addTaskViewModel = addTaskViewModel
        addTaskViewModel.getTime()
        addTaskViewModel.getDate()
    }
}
