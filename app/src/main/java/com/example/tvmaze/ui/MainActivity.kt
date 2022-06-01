package com.example.tvmaze.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.tvmaze.R
import com.example.tvmaze.databinding.ActivityMainBinding
import com.example.tvmaze.utils.visible
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mNavController: NavController
    private lateinit var mNavView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mNavView = binding.navView

        mNavController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
       /* val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_show, R.id.navigation_search, R.id.navigation_favorite
            )
        )
        setupActionBarWithNavController(mNavController, appBarConfiguration)*/
        mNavView.setupWithNavController(mNavController)

        setBottomNavVisibility()
    }

    private fun showBottomBar() {
        mNavView.visible = true
    }

    private fun hideBottomBar() {
        mNavView.visible = false
    }

    private fun setBottomNavVisibility(){
        mNavController.addOnDestinationChangedListener{ _, destination, _ ->
            when(destination.id){
                R.id.showDetails -> hideBottomBar()
                R.id.episodeDetails -> hideBottomBar()
                else -> showBottomBar()
            }
        }
    }
}