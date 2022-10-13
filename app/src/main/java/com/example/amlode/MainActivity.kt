package com.example.amlode

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.amlode.data.DeaResponse
import com.example.amlode.entities.DeaMarker
import com.example.amlode.fragments.MapFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
open class MainActivity : AppCompatActivity(){

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var markers : MutableList<DeaMarker>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.button_bar_menu)
        requestLocationPermission()
        markers = intent.getParcelableArrayListExtra<DeaMarker>("response") as ArrayList<DeaMarker>
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    //funcion publica para que tome el fragmeto map
    fun getMarkers() : MutableList<DeaMarker>{
        return this.markers
    }

    private fun requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                val permissionArray = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                requestPermissions(permissionArray, LOCATION_PERMISSION_REQUEST_CODE)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                showLocationPermissionRationleDialog()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun showLocationPermissionRationleDialog() {
        val dialog = AlertDialog.Builder(this)
            .setTitle("Necesitas permiso de Ubicacion para usar Amlode")
            .setMessage("Su ubicación es necesaria para brindarle una información certera")
            .setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermissions(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }.setNegativeButton("NO") { _, _ ->
                finish()
            }
        dialog.show()
    }

}





