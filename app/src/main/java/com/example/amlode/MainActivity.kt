package com.example.amlode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.amlode.entities.DeaMarker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

open class MainActivity : AppCompatActivity(){

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var markers : MutableList<DeaMarker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object{
        lateinit var prefs: SavedPreference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = SavedPreference(this)

        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.button_bar_menu)
        markers = intent.getParcelableArrayListExtra<DeaMarker>("response") as ArrayList<DeaMarker>
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    //funcion publica para que tome el fragmeto map
    fun getMarkers() : MutableList<DeaMarker>{
        return this.markers
    }

}





