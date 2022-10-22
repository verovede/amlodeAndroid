package com.example.amlode.fragments
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.amlode.R
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

        val map = MapFragment()
        //Todo agregar ubicacion usuario
        val longitud = viewFragment.findViewById<EditText>(R.id.longitud_ubicacion)
        val latitud = viewFragment.findViewById<EditText>(R.id.latitud_ubicacion)
        val titulo = viewFragment.findViewById<EditText>(R.id.dea_title)
        val modelo = viewFragment.findViewById<EditText>(R.id.modelo_dea)
        val info = viewFragment.findViewById<EditText>(R.id.info_dea)

        addDea = viewFragment.findViewById(R.id.add_dea)
        addDea.setOnClickListener{

        }
        deaImage = viewFragment.findViewById(R.id.dea_imagen)
        deaImage.setOnClickListener {
            openCamera()
        }
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
    /*
        private fun openDetailActivity(superhero: Superhero){
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.SUPERHERO_KEY, superhero)
            intent.putExtra(DetailActivity.BITMAP_KEY, picturePath)
            startActivity(intent)
        }
    */
    private val getContent = registerForActivityResult(ActivityResultContracts.TakePicture()){
            success ->
        if( success && picturePath.isNotEmpty()){
            deaBitmap = BitmapFactory.decodeFile(picturePath)
            deaImage.setImageBitmap(deaBitmap)
        }
    }

}