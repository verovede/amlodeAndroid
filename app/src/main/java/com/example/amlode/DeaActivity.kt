package com.example.amlode

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DeaActivity : AppCompatActivity() {
    lateinit var add_dea: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dea)
        add_dea = findViewById(R.id.add_dea)
    }
}