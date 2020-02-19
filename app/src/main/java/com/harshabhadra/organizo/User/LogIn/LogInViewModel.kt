package com.harshabhadra.organizo.User.LogIn

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.*

private lateinit var auth: FirebaseAuth

class LogInViewModel(private val context: Activity) : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private val logInViewmodelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + logInViewmodelJob)

    private var _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    fun logInUser(email:String, password:String){
        uiScope.launch {
            withContext(Dispatchers.IO){
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("LogInViewModel", "signInWithEmail:success")
                             _user.value = auth.currentUser
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("LogInViewModel", "signInWithEmail:failure", task.exception)
                            Toast.makeText(context, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                            _user.value = null
                        }
                    }
            }
        }
    }
    override fun onCleared() {
        super.onCleared()
        logInViewmodelJob.cancel()
    }
}