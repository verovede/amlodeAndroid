package com.example.amlode.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.amlode.LoginScreen
import com.example.amlode.MainActivity
import com.example.amlode.R
import com.example.amlode.entities.DeaMarker
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit  var map: GoogleMap
    private lateinit var markers: MutableList<DeaMarker>
    private lateinit var viewFragment : View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var buttonDea: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_map, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        markers = (activity as MainActivity).getMarkers()
        createFragment()
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        buttonDea = viewFragment.findViewById(R.id.button_dea)
        buttonDea.setOnClickListener(){
            val intent = Intent(requireActivity(), LoginScreen::class.java)
            startActivity(intent)
        }
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        setMarkers()
        findUser()
    }

    //TODO cambiar de acuerdo a los datamodels de fiware cuando este corriendo en la nube
    //setea markadores recibidos de API
    fun setMarkers(){
        val icon = getIcon()
        Log.w("MARCADORES RECIBIDOS", "DESDE API")
        for(dea in markers){
            //imprime datos de https://dea-get.herokuapp.com/api/deas/
            Log.w("dea ${dea!!.id}", "${dea.lat} ${dea.long}")
            val deaMarker = LatLng(dea.lat, dea.long)
            val deaName = "Dea ${dea.id}"
            map.addMarker(MarkerOptions().position(deaMarker).title(deaName).icon(icon))
        }
    }

    //TODO checkear el uso de permisos correctamente

    //Busca usuario en el mapa y lo ubica. Asume permisos. Usar GPS
    @SuppressLint("MissingPermission")
    private fun findUser(){
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val coordinates = LatLng(location!!.latitude, location!!.longitude)
                val marker = MarkerOptions().position(coordinates).title("Ud. está aquí")
                map.addMarker(marker)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
            }
    }

    //función que pasa a bitmap las imagenes para el marcador de maps
    private fun getIcon(): BitmapDescriptor {
        val drawable = ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.marker, null)
        drawable?.setBounds(0,0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap: Bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

}