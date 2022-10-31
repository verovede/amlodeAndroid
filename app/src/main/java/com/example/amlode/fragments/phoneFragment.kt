package com.example.amlode.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.amlode.R

class phoneFragment : Fragment() {
    lateinit var viewFragment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_phone, container, false)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        val manager: FragmentManager = requireActivity().supportFragmentManager
        val trans: FragmentTransaction = manager.beginTransaction()
        startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel: 911")));
        trans.remove(this)
        trans.commit()
        manager.popBackStack()
    }

}