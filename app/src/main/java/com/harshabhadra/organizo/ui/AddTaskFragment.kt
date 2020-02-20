package com.harshabhadra.organizo.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentAddTaskBinding

/**
 * A simple [Fragment] subclass.
 */
class AddTaskFragment : Fragment() {

    private lateinit var addTaskBinding: FragmentAddTaskBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        addTaskBinding = FragmentAddTaskBinding.inflate(inflater, container, false)

        return addTaskBinding.root
    }
}
