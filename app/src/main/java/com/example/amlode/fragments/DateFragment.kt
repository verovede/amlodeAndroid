package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.amlode.R
import com.example.amlode.SplashActivity

class DateFragment : AppCompatActivity() {
    lateinit var register_user: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_date)
        register_user= findViewById(R.id.register_user)
    }

    override fun onStart() {
        super.onStart()
        register_user.setOnClickListener{
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}