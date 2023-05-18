package com.example.khedma.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import com.example.khedma.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.khedma.viewmodel.repository.UserRepository

class Forgetpassword : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)
        val userRepository = UserRepository()

        //val modifyprofile = findViewById<ImageView>(R.id.modifyprofile)
        val ForgetBtn = findViewById<Button>(R.id.ForgetBtn)
        val emailText = findViewById<EditText>(R.id.EmailEditText2)



        ForgetBtn.setOnClickListener {

            val email = emailText.text.toString().trim()


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                emailText.requestFocus()
                return@setOnClickListener
            }else{

            CoroutineScope(Dispatchers.Main).launch {
                val success = userRepository.forgetpassword(this@Forgetpassword , email)
                if (success) {
                    // Registration successful, navigate to another page
                    val intent = Intent(this@Forgetpassword, CodeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }}
        }

    }
}