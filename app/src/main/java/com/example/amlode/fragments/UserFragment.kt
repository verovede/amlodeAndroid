package com.example.amlode.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.amlode.R
import com.example.amlode.SavedPreference

class UserFragment : Fragment() {

    private lateinit var viewFragment : View
    lateinit var username: TextView
    lateinit var email: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        username = viewFragment.findViewById(R.id.username)
        email = viewFragment.findViewById(R.id.email)
        username.text = SavedPreference.getSharedPreference(context)?.getString("username","")
        email.text = SavedPreference.getSharedPreference(context)?.getString("email","")
    }
}