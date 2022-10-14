package com.example.amlode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.amlode.api.APIService
import com.example.amlode.data.DeaResponse
import com.example.amlode.entities.DeaMarker
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
    private val markers = ArrayList<DeaMarker>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)
        callApi()
        intent = Intent(this, MainActivity::class.java)
    }


    //llama api realiza un intent hacia main (que pide permisos y carga hacia maps)
    private fun callApi() {
        val baseURL = "https://dea-get.herokuapp.com/api/deas/"
        val api = APIService.create(baseURL)

        api.getDeas()?.enqueue(object : Callback<ArrayList<DeaResponse?>?> {
            override fun onResponse(
                call: Call<ArrayList<DeaResponse?>?>,
                response: Response<ArrayList<DeaResponse?>?>
            ) {
                val response: ArrayList<DeaResponse?>? = (response.body())!!

                if (response != null) {
                    for(dea in response){
                        markers.add(DeaMarker(dea!!.id,dea!!.coordinate.latitude, dea!!.coordinate.longitude))
                    }
                    val bundle = Bundle()
                    bundle.putParcelableArrayList("response", markers)
                    intent.putExtras(bundle)
                    startActivity(intent)
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

}