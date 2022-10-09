package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.amlode.R
import org.w3c.dom.Text
import android.net.Uri;


class AidFragment : Fragment() {
    lateinit var viewFragment: View
    lateinit var buttonBack: Button
    lateinit var buttonCall: Button


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_aid, container, false)
        buttonBack = viewFragment.findViewById<Button>(R.id.button_primeros_aux)
        buttonCall = viewFragment.findViewById<Button>(R.id.button2)

        return viewFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        buttonCall.setOnClickListener(View.OnClickListener() {
            startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: 911")));
        });
    }
}

