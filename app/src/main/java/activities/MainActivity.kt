package activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.amlode.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_pantalla_ubicacion)
    }
}