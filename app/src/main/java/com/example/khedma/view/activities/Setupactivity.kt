package com.example.khedma.view.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.khedma.R
import com.example.khedma.viewmodel.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Setupactivity : AppCompatActivity() {
    private val REQUEST_CODE_SELECT_FILE = 123
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setupactivity)
        sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val userRepository = UserRepository()
        val pdf = findViewById<View>(com.example.khedma.R.id.pdf) as Button
        val employer = findViewById<View>(com.example.khedma.R.id.employer) as Button
        val employee = findViewById<View>(com.example.khedma.R.id.employee) as Button
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
        employer.setOnClickListener(){
            CoroutineScope(Dispatchers.Main).launch {

            val success = userRepository.setupemployer(this@Setupactivity , first_name, last_name,userId)
            if (success) {
                val intent = Intent(this@Setupactivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }}
        }
        employee.setOnClickListener(){
            CoroutineScope(Dispatchers.Main).launch {

                val success = userRepository.setupemployee(this@Setupactivity , first_name, last_name,userId)
                if (success) {
                    val intent = Intent(this@Setupactivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }}
        }
        pdf.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "application/pdf"

            startActivityForResult(intent, REQUEST_CODE_SELECT_FILE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SELECT_FILE && resultCode == RESULT_OK) {
            val fileUri = data?.data
            // Use the file URI as needed.
        }
    }
}