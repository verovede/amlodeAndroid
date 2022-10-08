package com.example.amlode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import com.example.amlode.R


class AidFragment : Fragment() {
    lateinit var viewFragment: View
    lateinit var buttonBack: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewFragment = inflater.inflate(R.layout.fragment_aid, container, false)
        buttonBack = viewFragment.findViewById<Button>(R.id.button_primeros_aux)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
    }

}