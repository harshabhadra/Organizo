package com.harshabhadra.organizo.User


import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController

import com.harshabhadra.organizo.R

/**
 * A simple [Fragment] subclass.
 */
class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_splash, container, false)
        Handler().postDelayed({
            view.findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLogInFragment())
        },2000)

        return view
    }
}
