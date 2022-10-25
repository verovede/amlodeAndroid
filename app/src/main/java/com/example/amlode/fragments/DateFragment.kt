package com.example.amlode.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.amlode.DatePickerFragment
import com.example.amlode.LoginScreen
import com.example.amlode.MainActivity.Companion.prefs
import com.example.amlode.R
import java.time.LocalDate
import java.time.Period

class DateFragment : Fragment() {
    lateinit var register_date: Button
    lateinit var date: EditText
    lateinit var age: TextView
    lateinit var viewFragment: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_date, container, false)
        findById()
        return viewFragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onStart() {
        super.onStart()
        date.setOnClickListener{
            showDatePickerDialog()
        }
    }

    private fun findById(){
        register_date =  viewFragment.findViewById(R.id.register_date)
        date = viewFragment.findViewById(R.id.date)
        age = viewFragment.findViewById(R.id.age_user)
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment { day, month, year -> onDateSelected(day, month+1, year) }
        getFragmentManager()?.let { datePicker.show(it, "DatePicker") }
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        date.setText("$day/$month/$year")
        prefs.saveDate("$day/$month/$year")

        val eighteenYearsAgo = LocalDate.now() - Period.ofYears(18)
        val pickedDate = LocalDate.of(year, month + 1, day)

        if (pickedDate < eighteenYearsAgo) {
            register_date.setEnabled(true)
            age.setVisibility(View.INVISIBLE)
            register_date.setOnClickListener {
                val intent = Intent(requireContext(), LoginScreen::class.java)
                startActivity(intent)
            }
        }else{
            register_date.setEnabled(false)
            age.setVisibility(View.VISIBLE)
        }
    }
}

