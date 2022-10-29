package com.example.amlode.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.amlode.*
import com.example.amlode.MainActivity.Companion.prefs
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
    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

    private lateinit var map: GoogleMap
    private lateinit var markers: MutableList<DeaMarker>
    private lateinit var userLocation: Location
    private lateinit var viewFragment : View
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var buttonDea: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewFragment = inflater.inflate(R.layout.fragment_map, container, false)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity().applicationContext)
        userLocation = Location("")
        markers = (activity as MainActivity).getMarkers()
        createFragment()
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        buttonDea = viewFragment.findViewById(R.id.button_dea)
        buttonDea.setOnClickListener{

            if(!prefs.getUsername().isEmpty()) {
                val action = MapFragmentDirections.actionMapFragmentToDeaFragment()
                viewFragment.findNavController().navigate(action)
            }else{
                val action = MapFragmentDirections.actionMapFragmentToDateFragment("actionLoginFragmentToMapFragment")
                viewFragment.findNavController().navigate(action)
            }
        }
    }

    private fun createFragment() {
        val mapFragment: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        Log.w("Map", "$markers")
        enableLocation()
    }

    //TODO cambiar de acuerdo a los datamodels de fiware cuando este corriendo en la nube
    //setea markadores recibidos de API
    private fun setMarkers(){
        val icon = getIcon()
        map.setInfoWindowAdapter(CustomInfoWindowAdapter(requireContext()))

        Log.w("MARCADORES RECIBIDOS", "DESDE API")
        Log.w("User Location", "$userLocation")
        Log.w("Map", "$markers")
        val loc = Location("")

        for(dea in markers){
            Log.w("dea ${dea.id}", "${dea.latitude} ${dea.longitude}")

            loc.longitude = dea.longitude
            loc.latitude = dea.latitude

            val distance = loc.distanceTo(userLocation)
            val coordinate = LatLng(dea.latitude, dea.longitude)
            val meters = String.format("%.2f", (distance / 1000 ))
            val markerOptions = MarkerOptions().position(coordinate)
                .title(dea.id)
                .snippet(dea.address)
                .icon(icon)
            map.addMarker(markerOptions)
        }
    }

    //Busca usuario en el mapa y lo ubica. Usar GPS
    @SuppressLint("MissingPermission")
    private fun setUserLocation(){
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if(location != null){
                        Log.w("LOCATION", "$location")
                        userLocation.latitude = location.latitude
                        userLocation.longitude = location.longitude
                        val coordinates = LatLng(location.latitude, location.longitude)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f))
                        setMarkers()
                    }
                }
    }

    //función que pasa a bitmap las imagenes para el marcador de maps
    private fun getIcon(): BitmapDescriptor {
        val drawable = ResourcesCompat.getDrawable(requireActivity().resources, R.drawable.puntero, null)
        drawable?.setBounds(0,0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap: Bitmap = Bitmap.createBitmap(drawable?.intrinsicWidth ?: 0, drawable?.intrinsicHeight ?: 0, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable?.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    private fun isLocationPermissionGranted() = ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if(!::map.isInitialized) return
        if(isLocationPermissionGranted()){
            init()
        }else{
            requestLocationPermission()
        }
    }

    @SuppressLint("MissingPermission")
    private fun init(){
        map.isMyLocationEnabled = true
        setUserLocation()
    }

    private fun requestLocationPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(requireContext(), "Acepta los Permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
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
                init()
            }else{
                Toast.makeText(requireContext(), "Acepta los Permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }


}