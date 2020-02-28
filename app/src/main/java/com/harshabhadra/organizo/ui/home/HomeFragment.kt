package com.harshabhadra.organizo.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.harshabhadra.organizo.User.LogInActivity
import com.harshabhadra.organizo.Viewanimation.initView
import com.harshabhadra.organizo.Viewanimation.rotateFab
import com.harshabhadra.organizo.Viewanimation.showIn
import com.harshabhadra.organizo.Viewanimation.showOut
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

        val application = requireNotNull(this.activity).application
        val homeViewModelFractory = HomeViewModelFractory(application)
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
        initView(homeBinding.addProjectLayout)
        initView(homeBinding.startTaskLayout)
        initView(homeBinding.saveTaskLayout)
        initView(homeBinding.transView)

        //Set on click listener to the visible fab button
        homeBinding.addFab.setOnClickListener {
            isRotate = rotateFab(it, !isRotate)
            if (isRotate) {
                showIn(homeBinding.startTaskLayout)
                showIn(homeBinding.saveTaskLayout)
                showIn(homeBinding.addProjectLayout)
                showIn(homeBinding.transView)
            }else{
                showOut(homeBinding.transView)
                showOut(homeBinding.addProjectLayout)
                showOut(homeBinding.startTaskLayout)
                showOut(homeBinding.saveTaskLayout)
            }
        }

        //Set on click listener to the add task fab button
        homeBinding.startTaskLayout.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment(true))
            isRotate = false
        }

        //Set on click listener to the save task fab button
        homeBinding.saveTaskLayout.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToAddTaskFragment(false))
        }

        //Set on click listener to the add project fab button
        homeBinding.addProjectsFab.setOnClickListener {
        }

        //Setting up task recyclerView
        val homeTaskAdapter = HomeTaskAdapter()
        homeBinding.homeTaskRecycler.adapter = homeTaskAdapter

        //Observe List of task from HomeViewModel
        homeViewModel.taskList.observe(viewLifecycleOwner, Observer {
            it?.let {
                homeTaskAdapter.submitList(it)
            }?:let {
                Toast.makeText(context,"No Recent Task", Toast.LENGTH_SHORT).show()
            } }
        )

        //Setting up Category RecyclerView
        val homeCategoryAdapter = HomeCategoryAdapter()
        homeBinding.categoryRecycler.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
        homeBinding.categoryRecycler.adapter = homeCategoryAdapter

        //Observe category list from HomeViewModel class
        homeViewModel.categoryList.observe(viewLifecycleOwner, Observer {
            it?.let{
                homeCategoryAdapter.submitList(it)
                homeBinding.emptyBox.visibility = View.INVISIBLE
                Toast.makeText(context,"Name: ${it[0].categoryName}", Toast.LENGTH_SHORT).show()
            }?:let {
                Toast.makeText(context,"Add new Categories", Toast.LENGTH_SHORT).show()
            }
        })
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
        homeBinding.homeViewModel = homeViewModel
        homeViewModel.getCurrentUser()
        homeViewModel.getToday()
    }
}
