package com.example.khedma.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.khedma.R

class Setupactivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setupactivity)
        val first_nameEditText = findViewById<View>(com.example.khedma.R.id.first_name) as EditText
        val first_name = first_nameEditText.text.toString()
        val last_nameEditText = findViewById<View>(com.example.khedma.R.id.last_name) as EditText
        val last_name = last_nameEditText.text.toString()
        val ageEditText = findViewById<View>(com.example.khedma.R.id.age) as EditText
        val age = ageEditText.text.toString()
        val phoneEditText = findViewById<View>(com.example.khedma.R.id.Phone) as EditText
        val phone = phoneEditText.text.toString()
        val addressEditText = findViewById<View>(com.example.khedma.R.id.address) as EditText
        val address = addressEditText.text.toString()
    }
}