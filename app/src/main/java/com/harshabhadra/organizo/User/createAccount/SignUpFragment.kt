package com.harshabhadra.organizo.User.createAccount


import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.harshabhadra.organizo.databinding.FragmentSignUpBinding

/**
 * A simple [Fragment] subclass.
 */
class SignUpFragment : Fragment() {

    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var name: String
    private lateinit var signUpBinding: FragmentSignUpBinding
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
            verifyCreandCreateAccount(name,email,password,confirmPassword)
        }

        //Set OnclickListener to already user button
        signUpBinding.alreadyUserButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToLogInFragment())
        }

        //Observing user LiveData
        signUpViewModel.user.observe(viewLifecycleOwner, Observer { user ->
            user?.let {
                user.displayName?.let {
                    Toast.makeText(context, "Welcome ${user.displayName}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
        
        return signUpBinding.root
    }

    fun verifyCreandCreateAccount(
        name: String,
        email: String,
        password: String,
        conPassword: String
    ){
        if (!(TextUtils.isEmpty(name)) && signUpViewModel.verifyEmail(email) && signUpViewModel.validatePassword(
            password,
            conPassword
        )){
            signUpViewModel.createAccount(name, email, password)
        }else if (!(TextUtils.isEmpty(name))){
            signUpBinding.signUpNameTextinput.setError("Enter Name")
        }else if (!signUpViewModel.verifyEmail(email)){
            signUpBinding.signUpEmailTextInput.setError("Enter a valid Email")
        }else if (!signUpViewModel.validatePassword(password, conPassword)){
            signUpBinding.signUpPasswordTextInput.setError("Password Mismatch")
        }
    }

    override fun onStart() {
        super.onStart()
        signUpViewModel.getCurrentUser()
    }
}
