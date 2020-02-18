package com.harshabhadra.organizo.User.createAccount

import android.app.Activity
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.*


private lateinit var auth: FirebaseAuth

class SignUpViewModel(private val context: Activity) : ViewModel() {

    init {
        auth = FirebaseAuth.getInstance()
    }

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    //Initializing FireBaseUser
    private var _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?>
        get() = _user

    //Creating Account with email and password
    fun createAccount(name: String, email: String, password: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(context) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("SignUpViewModel", "createUserWithEmail:success")
                            _user.value = auth.currentUser
                            updateProfileInfo(name,_user.value)
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e("SingUpViewModel", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(
                                context, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    //validate email
    fun verifyEmail(email: CharSequence):Boolean{
        return ((!TextUtils.isEmpty(email)) && Patterns.EMAIL_ADDRESS.matcher(email).matches())
    }

    //validate password
    fun validatePassword(password: String):Boolean{
        return ((!TextUtils.isEmpty(password)&& (password.length>=6)))
    }

    //Match password
    fun matchPassword(password: String, conPassword:String):Boolean{
        return (!TextUtils.isEmpty(conPassword) && conPassword == password)
    }
    //Update user profile
    private fun updateProfileInfo(userName: String, user: FirebaseUser?) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                val profileUpdates = UserProfileChangeRequest.Builder()
                    .setDisplayName(userName)
                    .build()

                user?.updateProfile(profileUpdates)
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _user.value = auth.currentUser
                        } else {
                            Toast.makeText(
                                context, "Cannot Update User Profile",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}