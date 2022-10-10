package com.example.amlode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.amlode.R


class PermitionsFragment : Fragment() {

    lateinit var viewFragment : View
    lateinit var btnAccept: Button
    //lateinit var btnDecline: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewFragment = inflater.inflate(R.layout.fragment_permitions, container, false)
        btnAccept = viewFragment.findViewById(R.id.button_permitions_accept)
        //btnDecline = viewFragment.findViewById(R.id.button_main_decline)

        return viewFragment
    }

    override fun onStart() {
        super.onStart()
    }

}