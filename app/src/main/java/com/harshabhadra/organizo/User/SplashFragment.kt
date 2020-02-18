package com.harshabhadra.organizo.User


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.harshabhadra.organizo.MainActivity

import com.harshabhadra.organizo.R

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    private lateinit var splashViewModel:SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_splash, container, false)

        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)
        Handler().postDelayed({
            splashViewModel.currentUser.observe(viewLifecycleOwner, Observer {user->
                user?.let {
                    val intent = Intent(activity, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }?:let { view.findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLogInFragment()) }
            })

        },2000)

        return view
    }

    override fun onStart() {
        super.onStart()
        splashViewModel.getCurrentUser()
    }
}
