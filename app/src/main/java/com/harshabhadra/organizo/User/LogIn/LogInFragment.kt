package com.harshabhadra.organizo.User.LogIn


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.FragmentLogInBinding
import com.harshabhadra.organizo.ui.MainActivity

/**
 * A simple [Fragment] subclass.
 */
class LogInFragment : Fragment() {

    private lateinit var logInViewModel: LogInViewModel
    private var loadingDialog: AlertDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val loginBinding = FragmentLogInBinding.inflate(inflater, container, false)

        val viewModelFactory = LogInViewModelFactory(activity!!)
        //Initializing ViewModel class
        logInViewModel = ViewModelProvider(this, viewModelFactory).get(LogInViewModel::class.java)

        //Observe the user object from view model class
        logInViewModel.user.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadingDialog?.dismiss()
                val intent = Intent(activity, MainActivity::class.java)
                startActivity(intent)
                activity?.finish()
            }?:let {
                loadingDialog?.dismiss()
            }
        })

        val creteAccountButton = loginBinding.createAccountButton
        //Set on click listener to the create account button
        creteAccountButton.setOnClickListener {
            findNavController().navigate(LogInFragmentDirections.actionLogInFragmentToSignUpFragment())
        }

        loginBinding.loginButton.setOnClickListener {
            val email = loginBinding.logInEmailTextInput.text.toString()
            val password = loginBinding.logInPasswordTextInput.text.toString()
            loadingDialog = createLoadingDialog()
            loadingDialog?.show()
            logInViewModel.logInUser(email, password)
        }

        return loginBinding.root
    }

    //Create Loading Dialog
    private fun createLoadingDialog(): AlertDialog? {
        val layout: View = LayoutInflater.from(context).inflate(R.layout.log_in_loading, null)
        val builder = context?.let { AlertDialog.Builder(it,R.style.loadingAlertDialog) }
        builder?.setView(layout)
        builder?.setCancelable(false)
        return builder?.create()
    }
}
