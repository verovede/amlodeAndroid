package com.example.amlode.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.amlode.R
import com.example.amlode.SavedPreference
import com.squareup.picasso.Picasso

class UserFragment : Fragment() {

    private lateinit var viewFragment : View
    lateinit var username: TextView
    lateinit var email: TextView
    lateinit var photo: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_user, container, false)
        username = viewFragment.findViewById(R.id.username)
        email = viewFragment.findViewById(R.id.email)
        photo =  viewFragment.findViewById(R.id.photo)
        return viewFragment
    }

    override fun onStart() {
        super.onStart()
        username.text = SavedPreference.getSharedPreference(context)?.getString("username","")
        email.text = SavedPreference.getSharedPreference(context)?.getString("email","")
        Picasso.with(context).load(SavedPreference.getPhoto()).into(photo)
    }
}