package com.example.amlode

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment(val listener: (day: Int, month: Int, year: Int) -> Unit) :
    DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var calendar = Calendar.getInstance()
        var year = calendar.get(Calendar.YEAR)
        var month = calendar.get(Calendar.MONTH)
        var day = calendar.get(Calendar.DAY_OF_MONTH)
        var datePicker = DatePickerDialog(requireContext(),R.style.DatePickerTheme, this, year, month, day)
        limitDate(calendar,year,datePicker)
        return datePicker
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        listener(day, month, year)
    }

    private fun limitDate(calendar: Calendar, year: Int, datePicker: DatePickerDialog){
        calendar.set(Calendar.YEAR, year - 100)
        datePicker.datePicker.setMinDate(calendar.timeInMillis)
        calendar.set(Calendar.YEAR, year + 0)
        datePicker.getDatePicker().setMaxDate(calendar.timeInMillis)
    }
}
