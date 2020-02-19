package com.harshabhadra.organizo.ui

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.harshabhadra.organizo.R
import com.harshabhadra.organizo.databinding.ActivityMainBinding
import com.infideap.drawerbehavior.Advance3DDrawerLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: Advance3DDrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainBinding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val toolbar = mainBinding.mainToolbar
        setSupportActionBar(toolbar)
        drawerLayout = mainBinding.mainDrawerLayout
        val navController = this.findNavController(R.id.main_nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController,drawerLayout)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)

        // prevent nav gesture if not on start destination
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, bundle: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        NavigationUI.setupWithNavController(mainBinding.mainNavView,navController)

        drawerLayout.setViewRotation(Gravity.START,10F)
        drawerLayout.setViewElevation(Gravity.START,15F)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.main_nav_host_fragment)
        return NavigationUI.navigateUp(navController,appBarConfiguration)
    }
}
