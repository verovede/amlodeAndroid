package fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.amlode.R

class fragment_primeros_aux : Fragment() {
    // TODO: Rename and change types of parameters

    lateinit var viewFragment : View
    lateinit var btnPrimerosAuxilios: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_primeros_aux, container, false)
        btnPrimerosAuxilios = viewFragment.findViewById(R.id.button2)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        btnPrimerosAuxilios.setOnClickListener() {
            val callIntent: Intent = Uri.parse("tel:911").let { number ->
                Intent(Intent.ACTION_DIAL, number)
            }

            startActivity(callIntent);

        }
    }
}