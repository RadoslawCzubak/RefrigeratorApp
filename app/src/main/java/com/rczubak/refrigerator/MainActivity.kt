package com.rczubak.refrigerator

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var userName: String
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)

        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController)

        setupBottomNavMenu(navController)



        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment) {
                bottom_nav.visibility = View.GONE
            } else {
                bottom_nav.visibility = View.VISIBLE
            }

            if (destination.id == R.id.ListFragment || destination.id == R.id.AddFragment){
                supportActionBar?.setDisplayShowHomeEnabled(false)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            else{
                supportActionBar?.setDisplayShowHomeEnabled(true)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottom_nav.let {
            NavigationUI.setupWithNavController(it, navController)
        }

    }

    override fun onSupportNavigateUp(): Boolean {

        return navController.navigateUp()
    }


}

