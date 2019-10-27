package com.rczubak.refrigerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    public lateinit var userName: String
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        navController= Navigation.findNavController(this,R.id.nav_host_fragment)

        setupBottomNavMenu(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            if (destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment) {
                bottom_nav.visibility = View.GONE
            } else {
                bottom_nav.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        val auth = FirebaseAuth.getInstance()
        auth.signOut()
    }

    private fun setupBottomNavMenu(navController: NavController){
        bottom_nav.let {
            NavigationUI.setupWithNavController(it, navController)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_layout)
    }
}
