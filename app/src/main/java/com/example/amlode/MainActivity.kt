package com.example.amlode

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.amlode.entities.DeaMarker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView

open class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var markers: MutableList<DeaMarker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.button_bar_menu)
        markers = intent.getParcelableArrayListExtra<DeaMarker>("response") as ArrayList<DeaMarker>
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
        navigate()
    }

    //funcion publica para que tome el fragmeto map
    fun getMarkers(): MutableList<DeaMarker> {
        return this.markers
    }

    //CONTROLAR BOTON BACK CELULAR PARA CERRAR LA APP
    override fun onBackPressed() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(R.string.dialog_salir)
            .setPositiveButton(R.string.dialog_salir_si,
                DialogInterface.OnClickListener { dialog, id ->
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.addCategory(Intent.CATEGORY_HOME)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                })
            .setNegativeButton(R.string.dialog_salir_no,
                DialogInterface.OnClickListener { dialog, id ->
                    dialog.dismiss();
                })
        // Create the AlertDialog object and return it
        builder.show()
    }

    private fun navigate() {
        bottomNavView.setOnItemSelectedListener{
            when (it.itemId) {
                R.id.aidFragment -> {
                    Navigation.findNavController(this, R.id.nav_host)
                        .navigate(R.id.aidFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.infoFragment -> {
                    Navigation.findNavController(this, R.id.nav_host)
                        .navigate(R.id.infoFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.mapFragment -> {
                    Navigation.findNavController(this, R.id.nav_host)
                        .navigate(R.id.mapFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.userFragment -> {
                    Navigation.findNavController(this, R.id.nav_host)
                        .navigate(R.id.userFragment)
                    return@setOnItemSelectedListener true
                }

                R.id.phoneFragment -> {
                    startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: 911")));
                    Log.d("ENTRA?", "ENTRA?")
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}