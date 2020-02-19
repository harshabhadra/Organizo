package com.harshabhadra.organizo.ui.home

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

private lateinit var auth: FirebaseAuth

class HomeViewModel(private val activity: Activity) : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val homeViewModleJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + homeViewModleJob)

    //Store current user
    private var _currentUser = MutableLiveData<FirebaseUser?>()
    val currentUser: LiveData<FirebaseUser?>
        get() = _currentUser

    private var _userName = MutableLiveData<String>()
    val userName : LiveData<String>
    get() = _userName

    //Function to get currentUser
    fun getCurrentUser() {
        uiScope.launch {
            _currentUser.value = auth.currentUser
            _userName.value = _currentUser.value?.displayName
        }
    }

    override fun onCleared() {
        super.onCleared()
        homeViewModleJob.cancel()
    }
}