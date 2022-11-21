package com.example.amlode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.amlode.api.APIService
import com.example.amlode.data.DeaResponse
import com.example.amlode.entities.DeaMarker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val markers = ArrayList<DeaMarker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this.applicationContext)

        intent = Intent(this, MainActivity::class.java)
        requestLocationPermission()
    }

    //llama api realiza un intent hacia main (que pide permisos y carga hacia maps)
    private fun callApi() {
        val api = APIService.createDeaAPI()

        api.getDeas()?.enqueue(object : Callback<ArrayList<DeaResponse?>?> {
            override fun onResponse(
                call: Call<ArrayList<DeaResponse?>?>,
                response: Response<ArrayList<DeaResponse?>?>
            ) {
                val response: ArrayList<DeaResponse?>? = (response.body())!!
                if (response != null) {
                    for(dea in response){
                        if(dea?.active?.value == true){
                            markers.add(DeaMarker(dea!!.id, dea!!.latitude.value.toDouble(), dea!!.longitude.value.toDouble(), dea!!.active.value, dea!!.datestamp.value, dea!!.address.value))
                        }
                    }
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("response", markers)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    finish();
                }
            }

            override fun onFailure(call: Call<ArrayList<DeaResponse?>?>, t: Throwable) {
                Toast.makeText (applicationContext,
                    "Se ha producido un error de carga",
                    Toast.LENGTH_SHORT)
                    .show();
            }
        })
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "Acepta los Permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
             callApi()
            }else{
                Toast.makeText(this, "Acepta los Permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }

}