package com.example.amlode.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.amlode.MainActivity
import com.example.amlode.R
import com.example.amlode.SplashActivity
import com.example.amlode.api.APIService
import com.example.amlode.data.BooleanValue
import com.example.amlode.data.Coordinate
import com.example.amlode.data.DeaResponse
import com.example.amlode.data.StringValue
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class DeaFragment : Fragment() {
    private lateinit var addDea: Button
    private lateinit var viewFragment : View
    private lateinit var deaImage: ImageView
    private var deaBitmap: Bitmap? = null
    private var picturePath = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_dea, container, false)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()

        val volverButton: Button = viewFragment.findViewById(R.id.volver_map)
        volverButton.setOnClickListener{
            val action = DeaFragmentDirections.actionDeaFragmentToMapFragment()
            viewFragment.findNavController().navigate(action)
        }

        //Todo agregar ubicacion usuario
        addDea = viewFragment.findViewById(R.id.add_dea)
        addDea.setOnClickListener {
            val newDea = getDeaInfo()
            val api = APIService.create()

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
                })
        }

        deaImage = viewFragment.findViewById(R.id.dea_imagen)
        deaImage.setOnClickListener {
            openCamera()
        }
    }

    private fun getDeaInfo(): DeaResponse {
        val longitud: Double = viewFragment.findViewById<EditText>(R.id.longitud_ubicacion).text.toString().toDouble()
        val latitud: Double = viewFragment.findViewById<EditText>(R.id.latitud_ubicacion).text.toString().toDouble()
        val titulo: String = viewFragment.findViewById<EditText>(R.id.dea_title).text.toString()
        val modelo: String = viewFragment.findViewById<EditText>(R.id.modelo_dea).text.toString()
        val info: String = viewFragment.findViewById<EditText>(R.id.info_dea).text.toString()
        val array: ArrayList<Double> = ArrayList<Double>(2)
        array.add(longitud)
        array.add(latitud)
        return DeaResponse(
            11,
            "dea",
            Coordinate("LatLng", array),
            StringValue("String", titulo),
            StringValue("String", modelo),
            StringValue("String", info),
            StringValue("String", "fecha"),
            StringValue("String", "fecha"),
            BooleanValue("Boolean", false)
        )
    }

    private fun openCamera() {
        //private createImageFile
        val file = createImageFile()
        val uri = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            FileProvider.getUriForFile(requireContext(), "com.example.amlode.provider", file)
        }else{
            Uri.fromFile(file)
        }
        //Usa private getContent
        getContent.launch(uri)
    }

    private fun createImageFile(): File {
        val fileName = "dea_image"
        val fileDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES )
        val file = File.createTempFile(fileName, ".jpg", fileDirectory)
        picturePath = file.absolutePath
        return file
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
            success ->
        if( success && picturePath.isNotEmpty()){
            deaBitmap = BitmapFactory.decodeFile(picturePath)
            deaImage.setImageBitmap(deaBitmap)
        }
    }
}