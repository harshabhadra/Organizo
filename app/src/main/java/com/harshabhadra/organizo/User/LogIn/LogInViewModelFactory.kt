package com.harshabhadra.organizo.User.LogIn

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class LogInViewModelFactory(private val context: Activity) :ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LogInViewModel::class.java)){
            return LogInViewModel(context)as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}