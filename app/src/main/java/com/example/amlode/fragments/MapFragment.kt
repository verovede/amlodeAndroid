package com.example.amlode.fragments

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.example.amlode.DeaMarker
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_map, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        createFragment()
        return viewFragment
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    @SuppressLint("MissingPermission")
    private fun createMarker() {
        //Marcadores de Prueba
        //Todo reemplazar por API Service
        //Todo conseguir ubicacion actual
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

        //usar en emulador fakeGps
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val coordinates = LatLng(location!!.latitude, location!!.longitude)
                val marker = MarkerOptions().position(coordinates).title("Ud. está aquí")
                map.addMarker(marker)
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
            }
    }

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