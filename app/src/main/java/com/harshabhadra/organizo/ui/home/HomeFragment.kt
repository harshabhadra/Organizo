package com.harshabhadra.organizo.ui.home


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

        homeBinding.emptyBox.setOnClickListener {
            Toast.makeText(
                context,
                "Add your task or projects and stay organize",
                Toast.LENGTH_LONG
            ).show()
        }

        homeBinding.addTaskFab.setOnClickListener {
            this.findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment())
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

    override fun onStart() {
        super.onStart()
        homeViewModel.getCurrentUser()
    }
}
