package com.harshabhadra.organizo.User.createAccount

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class SignUpViewModelFactory (private val context: Activity):ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)){
            return SignUpViewModel(context)as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }

}