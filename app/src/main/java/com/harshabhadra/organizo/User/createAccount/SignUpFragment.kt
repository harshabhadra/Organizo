package com.harshabhadra.organizo.User.createAccount


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.harshabhadra.organizo.MainActivity

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

        val email = signUpBinding.signUpEmailTextInput.text.toString()
        val password = signUpBinding.signUpPasswordTextInput.text.toString()
        if(activity != null) {
            val viewModelFactory = SignUpViewModelFactory(activity!!)
            //Initializing the SignUpViewModel class
            signUpViewModel = ViewModelProvider(this,viewModelFactory).get(SignUpViewModel::class.java)

        }

        signUpBinding.signUpButton.setOnClickListener {
            signUpViewModel.createAccount(email, password)
        }
        signUpViewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
//                val intent = Intent(activity,MainActivity::class.java)
//                startActivity(intent)
                Toast.makeText(context,"Welcome ${it.email}",Toast.LENGTH_SHORT).show()

            }?:let {
                Toast.makeText(context,"Unable to create User",Toast.LENGTH_SHORT).show()
            }
        })
        return signUpBinding.root
    }

    override fun onStart() {
        super.onStart()
        signUpViewModel.getCurrentUser()
    }
}
