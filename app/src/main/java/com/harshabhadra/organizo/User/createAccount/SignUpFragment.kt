package com.harshabhadra.organizo.User.createAccount


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentSignUpBinding

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel:SignUpViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val signUpBinding = FragmentSignUpBinding.inflate(inflater,container,false)

        //Initializing the SignUpViewModel class
        signUpViewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        return signUpBinding.root
    }
}
