package com.example.amlode.fragments
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.amlode.R
import android.Manifest.permission.CALL_PHONE
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class phoneFragment : Fragment() {
    lateinit var viewFragment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_aid, container, false)

        return viewFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel: 911")));
    }

}