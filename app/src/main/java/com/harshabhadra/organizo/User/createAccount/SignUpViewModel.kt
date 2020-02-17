package com.harshabhadra.organizo.User.createAccount

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


private lateinit var auth: FirebaseAuth
class SignUpViewModel(private val context: Activity) : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private var _user = MutableLiveData<FirebaseUser?>()
    val user:LiveData<FirebaseUser?>
    get() = _user

    //Function to get currentUser
    fun getCurrentUser(){
        _user.value = auth.currentUser
    }

    fun createAccount(email:String = "harshasharkey@gmail.com", password:String = "123" ){
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(context){task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignUpViewModel", "createUserWithEmail:success")
                     _user.value = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SingUpViewModel", "createUserWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
    }
}