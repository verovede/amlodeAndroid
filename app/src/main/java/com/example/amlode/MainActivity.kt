package com.example.amlode

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.amlode.api.APIService
import com.example.amlode.entities.DeaMarker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


open class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var markers: MutableList<DeaMarker>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        lateinit var prefs: SavedPreference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callPostPersistent()
        prefs = SavedPreference(this)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        bottomNavView = findViewById(R.id.button_bar_menu)
        markers = intent.getParcelableArrayListExtra<DeaMarker>("response") as ArrayList<DeaMarker>
        NavigationUI.setupWithNavController(bottomNavView, navHostFragment.navController)
    }

    //funcion publica para que tome el fragmeto map
    fun getMarkers(): MutableList<DeaMarker> {
        return this.markers
    }

    private fun callPostPersistent() {
        val apiPersistent = APIService.createPersistent()
        val persistent = createPersistent()
        apiPersistent.postSubscriptions(persistent)?.enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Post")
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    Log.w("SUCCESS", "SUCCESS Call Post")
                    Log.w("response ", "$response")
                }
            }
        )
    }

    private fun createPersistent(): String {
        val body = """{
                    "subject": {
                        "entities": [
                            {
                                "idPattern": ".*"
                            }
                        ]
                    },
                    "notification": {
                        "http": {
                            "url": "http://cygnus:5055/notify"
                        },
                        "attrsFormat": "legacy"
                    }
                }"""
        return body
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
}