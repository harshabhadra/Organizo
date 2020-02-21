package com.harshabhadra.organizo.ui


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.harshabhadra.organizo.MainNavigation

import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.Viewanimation
import com.harshabhadra.organizo.Viewanimation.initView
import com.harshabhadra.organizo.Viewanimation.rotateFab
import com.harshabhadra.organizo.Viewanimation.showIn
import com.harshabhadra.organizo.Viewanimation.showOut
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {

    private lateinit var addTaskBinding: FragmentAddTaskBinding
    private var isRotate = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)

        //hiding views
        initView(addTaskBinding.startTaskLayout)
        initView(addTaskBinding.saveTaskLayout)
        initView(addTaskBinding.addTransView)

        addTaskBinding.chooseFab.setOnClickListener {
            isRotate = rotateFab(it,!isRotate)
            if (isRotate){
                showIn(addTaskBinding.startTaskLayout)
                showIn(addTaskBinding.saveTaskLayout)
                showIn(addTaskBinding.addTransView)
            }else{
                showOut(addTaskBinding.startTaskLayout)
                showOut(addTaskBinding.saveTaskLayout)
                showOut(addTaskBinding.addTransView)
            }
        }
        return addTaskBinding.root
    }
}
