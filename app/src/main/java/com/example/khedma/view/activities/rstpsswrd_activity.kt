package com.example.khedma.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.khedma.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.khedma.viewmodel.repository.UserRepository

class rstpsswrd_activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pass)
        val userRepository = UserRepository()

        //val modifyprofile = findViewById<ImageView>(R.id.modifyprofile)
        val btn_Confirm = findViewById<Button>(R.id.btn_Confirm)
        val password1 = findViewById<EditText>(R.id.passwordchangeInputEditText)
        val confirmpassword = findViewById<EditText>(R.id.passwordchangeInputEditText2)

        val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val code = sharedPref.getString("code", null)

        btn_Confirm.setOnClickListener {
            val password = password1.text.toString().trim()
            val confirmPassword = confirmpassword.text.toString().trim()
            if (password != confirmPassword) {
                password1.error = "Incorrect"
                password1.requestFocus()
                confirmpassword.error = "Passwords do not match"
                confirmpassword.requestFocus()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                val success = userRepository.rstpassword(this@rstpsswrd_activity , code, password)
                if (success) {
                    // Registration successful, navigate to another page
                    val intent = Intent(this@rstpsswrd_activity, Singin_up_Activity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}