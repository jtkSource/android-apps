package com.jtk.android.myfirstapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

//Activity is just a screen
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var timesClicked = 0;
        val btnClickMe  = findViewById<Button>(R.id.myButton)
        val tvMyTextview = findViewById<TextView>(R.id.myTextView)

        btnClickMe.text = "Count Items!!"
        tvMyTextview.text = "$timesClicked items!!"
        btnClickMe.setOnClickListener{
            timesClicked++;
            tvMyTextview.text = "$timesClicked items!!"
            Toast.makeText(this, "Counting is going on!!", Toast.LENGTH_LONG).show()
        }
    }
}