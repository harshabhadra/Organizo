package com.harshabhadra.organizo.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private lateinit var auth: FirebaseAuth

class HomeViewModel : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private var _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    fun getCurrentUser(){
        _currentUser.value = auth.currentUser
    }
}