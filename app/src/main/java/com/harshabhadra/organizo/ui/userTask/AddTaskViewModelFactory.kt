package com.harshabhadra.organizo.ui.userTask

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class AddTaskViewModelFactory (private  val context:Application):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddTaskViewModel::class.java)){
            return AddTaskViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}