package com.example.amlode.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import com.example.amlode.SplashActivity
import com.example.amlode.api.APIService
import com.example.amlode.data.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList

class DeaFragment : Fragment() {

    private lateinit var addDea: Button
    private lateinit var viewFragment: View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var userLocation: Location
    private lateinit var longitud: EditText
    private lateinit var latitud: EditText
    private lateinit var direccion: EditText
    private val api = APIService.createUserAPI()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
        userLocation = Location("")
        viewFragment = inflater.inflate(R.layout.fragment_dea, container, false)
        setUserCoordinates()
        findById()
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        val volverButton: Button = viewFragment.findViewById(R.id.volver_map)
        volverButton.setOnClickListener {
            val action = DeaFragmentDirections.actionDeaFragmentToMapFragment()
            viewFragment.findNavController().navigate(action)
        }

        //Todo agregar ubicacion usuario
        addDea = viewFragment.findViewById(R.id.add_dea)
        addDea.setOnClickListener {
            val newDea = getDeaInfo()
            val api = APIService.createDeaAPI()

            Log.w("deaCreado", "$newDea")

            api.postDea(newDea)?.enqueue(
                object : Callback<Void> {
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.w("FAILURE", "Failure Call Post")
                    }

                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Log.w("SUCCESS", "SUCCESS Call Post")
                        val intent = Intent(requireContext(), SplashActivity::class.java)
                        startActivity(intent)
                    }
                }
            )
            callUserByEmail(newDea.id)
        }

    }

    private fun findById() {
        longitud = viewFragment.findViewById(R.id.longitud_ubicacion)
        latitud = viewFragment.findViewById(R.id.latitud_ubicacion)
        direccion = viewFragment.findViewById(R.id.direccion_encontrada)
    }

    private fun setCoordinatesText(latitude: Double, longitude: Double) {
        longitud.setText(latitude.toString())
        latitud.setText(longitude.toString())
        setAdress(latitude, longitude)
    }

    private fun setAdress(latitude: Double, longitude: Double) {
        val geocoder = Geocoder(requireContext().applicationContext, Locale.getDefault())
        val address: List<Address> = geocoder.getFromLocation(latitude, longitude, 1)
        direccion.setText(address[0].getAddressLine(0))
    }

    private fun getDeaInfo(): DeaResponse {
        val longitud: String =
            viewFragment.findViewById<EditText>(R.id.longitud_ubicacion).text.toString()
        val latitud: String =
            viewFragment.findViewById<EditText>(R.id.latitud_ubicacion).text.toString()
        val address: String =
            viewFragment.findViewById<EditText>(R.id.direccion_encontrada).text.toString()
        val date: String =
            "${LocalDateTime.now().dayOfMonth}/${LocalDateTime.now().month.ordinal + 1}/${LocalDateTime.now().year}"
        val idDea: Int = prefs.getSizeDeas() + 1
        return DeaResponse(
            "${idDea}",
            "dea",
            BooleanValue("Boolean", true),
            StringValue("String", "$address"),
            StringValue("String", "$date"),
            StringValue("String", "$longitud"),
            StringValue("String", "$latitud")
        )
    }

    @SuppressLint("MissingPermission")
    private fun setUserCoordinates() {
        var coordinates: LatLng
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.w("LOCATION", "$location")
                    userLocation.latitude = location.latitude
                    userLocation.longitude = location.longitude
                    coordinates = LatLng(location.latitude, location.longitude)
                    setCoordinatesText(userLocation.latitude, userLocation.longitude)
                }
            }
    }

    private fun callUserByEmail(idDeaAggregate: String) {
        api.getUser("v2/entities/${prefs.getEmail()}?type=user")
            ?.enqueue(object : Callback<UserResponse?> {
                override fun onResponse(call: Call<UserResponse?>, user: Response<UserResponse?>) {
                    val user: UserResponse? = (user.body())!!
                    if (user != null) {
                        val userUpdated = createPatchUser(user.deas.value, user.points.value, idDeaAggregate)
                        callPatchUser(prefs.getEmail(), userUpdated)
                        prefs.savePoints(user.points.value.toInt())
                    }
                }
                override fun onFailure(call: Call<UserResponse?>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Post")
                }
            })
    }

    private fun createPatchUser(deas: ArrayList<String>, points: Number, deaId : String): PatchUser {
        deas.add(deaId)
        return PatchUser(
            ArrayValue("StructuredValue", deas),
            NumberValue("Number", points.toInt() + 50)
        )
    }

    private fun callPatchUser(email: String, user: PatchUser) {
        api.patchUser(email, user)?.enqueue(
            object : Callback<Void> {
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.w("FAILURE", "Failure Call Post")
                }

                override fun onResponse(
                    call: Call<Void>,
                    response: Response<Void>
                ) {
                    Log.w("SUCCESS", "SUCCESS Call Post")
                }
            }
        )
    }
}
