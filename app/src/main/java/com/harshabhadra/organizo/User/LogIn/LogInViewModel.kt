package com.harshabhadra.organizo.User.LogIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private lateinit var auth: FirebaseAuth

class LogInViewModel :ViewModel(){

    init {
        auth = FirebaseAuth.getInstance()
    }

    //Store current user
    private var _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    //Function to get currentUser
    fun getCurrentUser(){
        _currentUser.value = auth.currentUser
    }
}