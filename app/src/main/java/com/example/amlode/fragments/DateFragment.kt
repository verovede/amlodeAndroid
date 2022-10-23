package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.amlode.DatePickerFragment
import com.example.amlode.LoginScreen
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import com.example.amlode.SplashActivity
import java.time.LocalDate
import java.time.Period

class DateFragment : AppCompatActivity() {
    lateinit var register_date: Button
    lateinit var date: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_date)
        register_date = findViewById(R.id.register_date)
        date = findViewById(R.id.date)

        date.setOnClickListener{
            showDatePickerDialog()
        }

        register_date.setOnClickListener {
            val intent = Intent(this, LoginScreen::class.java)
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

        val eighteenYearsAgo = LocalDate.now() - Period.ofYears(18)
        val pickedDate = LocalDate.of(year, month + 1, day)

        if (pickedDate < eighteenYearsAgo) {
            register_date.setVisibility(View.VISIBLE);
            register_date.setOnClickListener {
                val intent = Intent(this, LoginScreen::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}

