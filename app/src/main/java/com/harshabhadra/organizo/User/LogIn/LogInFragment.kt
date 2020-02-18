package com.harshabhadra.organizo.User.LogIn


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.harshabhadra.organizo.MainActivity

import com.harshabhadra.organizo.databinding.FragmentLogInBinding

/**
 * A simple [Fragment] subclass.
 */
class LogInFragment : Fragment() {

    private lateinit var logInViewModel: LogInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val loginBinding = FragmentLogInBinding.inflate(inflater, container, false)

        val viewModelFactory =  LogInViewModelFactory(activity!!)
        //Initializing ViewModel class
        logInViewModel = ViewModelProvider(this,viewModelFactory).get(LogInViewModel::class.java)

        //Observe the user object from view model class
        logInViewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                val  intent = Intent(activity,MainActivity::class.java)
                startActivity(intent)
            }
        })

        val creteAccountButton = loginBinding.createAccountButton
        //Set on click listener to the create account button
        creteAccountButton.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }

        loginBinding.loginButton.setOnClickListener {
            val email= loginBinding.logInEmailTextInput.text.toString()
            val password = loginBinding.logInPasswordTextInput.text.toString()
            logInViewModel.logInUser(email, password)
        }

        return loginBinding.root
    }
}
