package com.example.amlode.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.amlode.R


class MainFragment : Fragment() {

    lateinit var viewFragment : View
    lateinit var btnDea: View
    lateinit var btnUser: View
    lateinit var btnAid: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_main, container, false)


        return viewFragment
    }

    override fun onStart() {
        super.onStart()

    }
}