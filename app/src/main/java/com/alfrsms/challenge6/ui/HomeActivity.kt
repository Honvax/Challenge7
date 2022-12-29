package com.alfrsms.challenge6.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.alfrsms.challenge6.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var analytics: FirebaseAnalytics
    private lateinit var navController: NavController
    private lateinit var bottomNav: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_container) as NavHostFragment

        navController = navHostFragment.navController

        bottomNav = findViewById(R.id.bottom_nav_view)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.homeFragment,
            R.id.searchFragment,
            R.id.profileFragment
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
        supportActionBar?.hide()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.detailFragment -> hideBottomNav(true)
                R.id.updateProfileFragment -> hideBottomNav(true)
                else -> hideBottomNav(false)
            }
            analytics = Firebase.analytics
        }
    }

    private fun hideBottomNav(hide: Boolean) {
        if (hide) {
            bottomNav.visibility = View.GONE
        } else {
            bottomNav.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}