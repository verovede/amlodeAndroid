package com.example.amlode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

open class MainActivity : AppCompatActivity(){
    lateinit var bottomNavView : BottomNavigationView
    lateinit var navHostFragment : NavHostFragment
    private lateinit var buttonDea: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.button_bar_menu)
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    override fun onStart() {
        super.onStart()
        buttonDea = findViewById(R.id.button_dea)
        buttonDea.setOnClickListener(){
            val intent = Intent (this, LoginScreen::class.java)
            startActivity(intent)
        }
    }
}







