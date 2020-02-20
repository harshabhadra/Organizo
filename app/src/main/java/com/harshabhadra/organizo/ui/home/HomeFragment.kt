package com.harshabhadra.organizo.ui.home


import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.harshabhadra.organizo.User.LogInActivity
import com.harshabhadra.organizo.databinding.FragmentHomeBinding


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private var isRotate = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)

        val homeViewModelFractory = HomeViewModelFractory(activity!!)
        homeViewModel =
            ViewModelProvider(this, homeViewModelFractory).get(HomeViewModel::class.java)

        setHasOptionsMenu(true)
        homeBinding.homeViewModel = homeViewModel
        homeBinding.lifecycleOwner = this

        //Set on Click listener to box
        homeBinding.emptyBox.setOnClickListener {
            Toast.makeText(
                context,
                "Add your task or projects and stay organize",
                Toast.LENGTH_LONG
            ).show()
        }

        //Hiding views
        init(homeBinding.addProjectLayout)
        init(homeBinding.addTaskLayout)
        init(homeBinding.transView)

        //Set on click listener to the visible fab button
        homeBinding.addFab.setOnClickListener {
            isRotate = rotateFab(it, !isRotate)
            if (isRotate) {
                showIn(homeBinding.addTaskLayout)
                showIn(homeBinding.addProjectLayout)
                showIn(homeBinding.transView)
            }else{
                showOut(homeBinding.transView)
                showOut(homeBinding.addProjectLayout)
                showOut(homeBinding.addTaskLayout)
            }
        }

        //Set on click listener to the add task fab button
        homeBinding.addTaskFab.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment())
        }

        //Set on click listener to the add project fab button
        homeBinding.addProjectsFab.setOnClickListener {
        }
        return homeBinding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(com.harshabhadra.organizo.R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId
        if (id == com.harshabhadra.organizo.R.id.action_log_out) {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, LogInActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
        return true
    }

    private fun rotateFab(v: View, rotate: Boolean): Boolean {
        v.animate().setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
            })
            .rotation(if (rotate) 135f else 0f)
        return rotate
    }

    fun showIn(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 0f
        v.translationY = v.height.toFloat()
        v.animate()
            .setDuration(200)
            .translationY(0F)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                }
            })
            .alpha(1f)
            .start()
    }

    fun showOut(v: View) {
        v.visibility = View.VISIBLE
        v.alpha = 1f
        v.translationY = 0F
        v.animate()
            .setDuration(200)
            .translationY(v.height.toFloat())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    v.visibility = View.GONE
                    super.onAnimationEnd(animation)
                }
            }).alpha(0f)
            .start()
    }

    fun init(v: View) {
        v.visibility = View.GONE
        v.translationY = v.height.toFloat()
        v.alpha = 0f
    }

    override fun onStart() {
        super.onStart()
        homeViewModel.getCurrentUser()
    }
}
