package com.jtk.android.brithdayapp

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvDateSelected : TextView? = null
    private var tvTimeInMin : TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnDatePicker: Button = findViewById(R.id.buttonDatePicker)
        tvDateSelected  = findViewById(R.id.tvSelectedDate)
        tvTimeInMin = findViewById(R.id.tvTimeInMin)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }

    }

    private fun clickDatePicker() {

        val myCalendar = Calendar.getInstance();
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            { view, year, month, dayOfMonth ->
                val selectedDate = "$dayOfMonth/${month - 1}/$year"
                tvDateSelected?.text = selectedDate
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                theDate.let {
                    val selectedDateInMin = theDate.time / 60_000
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                    currentDate.let {
                        val currentDateInMin = currentDate.time / 60_000
                        val timePeriodInMin = currentDateInMin - selectedDateInMin
                        tvTimeInMin?.text = timePeriodInMin.toString()
                    }

                }
            }, year, month, day
        )
        dpd.datePicker.maxDate = System.currentTimeMillis() - 86400000 // limit
        dpd.show()

    }
}