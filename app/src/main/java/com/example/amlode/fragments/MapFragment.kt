package com.example.amlode.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.core.content.res.ResourcesCompat
import com.example.amlode.DeaMarker
import com.example.amlode.LoginScreen
import com.example.amlode.R
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
    private lateinit var viewFragment : View
    private val markers = mutableListOf<DeaMarker>()
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var buttonDea: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_map, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
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
        createMarker()
        findUser()
    }

    //Crea marcadores de Prueba
    private fun createMarker() {
        //Todo reemplazar por API Service
        createFakeMarkers()
        val icon = getIcon()
        for(dea in markers){
            val deaMarker = LatLng(dea.lat, dea.long)
            val deaName = dea.id
            val deaLocation = Location("")
            deaLocation.latitude = dea.lat
            deaLocation.longitude = dea.long
            map.addMarker(MarkerOptions().position(deaMarker).title(deaName).icon(icon))
        }
    }

    //Busca usuario en el mapa y lo ubica. Asume permisos
    @SuppressLint("MissingPermission")
    private fun findUser(){
        //usar en emulador de Gps
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


    private fun createFakeMarkers() {
        markers.add(DeaMarker("Dea 1", -34.5009741,-58.4972707))
        markers.add(DeaMarker("Dea 2", -34.489249083657384, -58.49677717308122))
        markers.add(DeaMarker("Dea 3", -34.48330932945249, -58.498628912880726))
        markers.add(DeaMarker("Dea 4", -34.48025894970691, -58.5020493713585))
        markers.add(DeaMarker("Dea 5", -34.48855368656378, -58.49459053094968))
    }


}