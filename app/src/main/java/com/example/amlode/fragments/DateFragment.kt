package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.amlode.DatePickerFragment
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import com.example.amlode.SplashActivity

class DateFragment : AppCompatActivity() {
    lateinit var register_user: Button
    lateinit var date: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_date)
        register_user = findViewById(R.id.register_user)
        date = findViewById(R.id.date)

        date.setOnClickListener{
            showDatePickerDialog()
        }

        register_user.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
        datePicker.show(supportFragmentManager, "datePicker")

    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        date.setText("$day/$month/$year")
        prefs.saveDate("$day/$month/$year")
    }
}

