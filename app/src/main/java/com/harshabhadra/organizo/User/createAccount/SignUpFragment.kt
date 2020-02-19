package com.harshabhadra.organizo.User.createAccount

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentSignUpBinding
import com.harshabhadra.organizo.ui.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment(), View.OnKeyListener {

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var name: String
    private lateinit var signUpBinding: FragmentSignUpBinding
    private var loadingDialog: AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        signUpBinding = FragmentSignUpBinding.inflate(inflater, container, false)

        if (activity != null) {
            val viewModelFactory = SignUpViewModelFactory(activity!!)
            //Initializing the SignUpViewModel class
            signUpViewModel =
                ViewModelProvider(this, viewModelFactory).get(SignUpViewModel::class.java)
        }

        //Create Account on signUp button Clicked
        signUpBinding.signUpButton.setOnClickListener {
            val email = signUpBinding.signUpEmailTextInput.text.toString()
            val password = signUpBinding.signUpPasswordTextInput.text.toString()
            val confirmPassword = signUpBinding.signUpConfirmPasswordTextInput.text.toString()
            name = signUpBinding.signUpNameTextinput.text.toString()
            verifyCredAndCreateAccount(name, email, password, confirmPassword)
        }

        //Set OnclickListener to already user button
        signUpBinding.alreadyUserButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
        }

        //Observing user LiveData
        signUpViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                user.displayName?.let {
                    loadingDialog?.dismiss()
                   val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        })

        signUpBinding.signUpNameTextinput.setOnKeyListener(this)
        signUpBinding.signUpEmailTextInput.setOnKeyListener(this)
        signUpBinding.signUpPasswordTextInput.setOnKeyListener(this)
        signUpBinding.signUpConfirmPasswordTextInput.setOnKeyListener(this)
        return signUpBinding.root
    }

    private fun verifyCredAndCreateAccount(
        name: String,
        email: String,
        password: String,
        conPassword: String
    ) {
        when {
            !(TextUtils.isEmpty(name)) && signUpViewModel.verifyEmail(email) && signUpViewModel.validatePassword(
                password
            ) && signUpViewModel.matchPassword(password, conPassword)
            -> {
                loadingDialog = createLoadingDialog()
                loadingDialog?.show()
                signUpViewModel.createAccount(name, email, password)
            }
            TextUtils.isEmpty(name) -> {
                signUpBinding.signUpNameTextinputLayout.error = "Enter Name"
            }
            !signUpViewModel.verifyEmail(email) -> {
                signUpBinding.signUpEmailTextInputLayout.error = "Enter a valid Email"
            }
            !signUpViewModel.validatePassword(password) -> {
                signUpBinding.signUpPasswordTextInputLayout.error = "Enter a valid Password"
            }
            !signUpViewModel.matchPassword(password, conPassword) -> {
                signUpBinding.signUpConfirmPasswordTextInputLayout.error = "Password Mismatch"
            }
        }
    }

    //Create Loading Dialog
    private fun createLoadingDialog(): AlertDialog? {
        val layout: View = LayoutInflater.from(context).inflate(R.layout.loading_layout, null)
        val builder = context?.let { AlertDialog.Builder(it) }
        builder?.setView(layout)
        builder?.setCancelable(false)
        return builder?.create()
    }

    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
        when (v) {
            signUpBinding.signUpNameTextinput -> signUpBinding.signUpNameTextinputLayout.isErrorEnabled =
                false
            signUpBinding.signUpEmailTextInput -> signUpBinding.signUpEmailTextInputLayout.isErrorEnabled =
                false
            signUpBinding.signUpPasswordTextInput -> signUpBinding.signUpPasswordTextInputLayout.isErrorEnabled =
                false
            signUpBinding.signUpConfirmPasswordTextInput -> signUpBinding.signUpConfirmPasswordTextInputLayout.isErrorEnabled =
                false
        }
        return false
    }
}
