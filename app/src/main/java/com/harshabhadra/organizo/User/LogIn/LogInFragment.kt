package com.harshabhadra.organizo.User.LogIn


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

        //Initializing ViewModel class
        logInViewModel = ViewModelProvider(this).get(LogInViewModel::class.java)

        val creteAccountButton = loginBinding.createAccountButton
        creteAccountButton.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }
        return loginBinding.root
    }

    override fun onStart() {
        super.onStart()
        logInViewModel.getCurrentUser()
        logInViewModel.currentUser.observe(viewLifecycleOwner, Observer { user->
              user?.displayName?.let {
                Toast.makeText(context,"Welcome ${user.displayName}",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
