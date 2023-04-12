package com.example.myapplication.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.myapplication.viewModel.repository.UserRepository

class editprofile : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editprofile)
        val userRepository = UserRepository()

        //val modifyprofile = findViewById<ImageView>(R.id.modifyprofile)
        val editprofile = findViewById<Button>(R.id.editprofile)
        val password1 = findViewById<EditText>(R.id.password1)
        val confirmpassword = findViewById<EditText>(R.id.confirmpassword)
        val emailText = findViewById<EditText>(R.id.editemail)

        val sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val userEmail = sharedPreferences.getString("userEmail", "")

        val backprofile = findViewById<ImageView>(R.id.backprofile)

        val editemailTextView = findViewById<EditText>(R.id.editemail)
        editemailTextView.setText(userEmail)
        editprofile.setOnClickListener {
            val password = password1.text.toString().trim()
            val confirmPassword = confirmpassword.text.toString().trim()
            val email = emailText.text.toString().trim()
            val id = userId.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                // Show an alert dialog if either field is empty
                AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Please enter both email and password")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailText.error = "Please enter a valid email address"
                emailText.requestFocus()
                return@setOnClickListener
            }
            if (password != confirmPassword) {
                password1.error = "Incorrect"
                password1.requestFocus()
                confirmpassword.error = "Passwords do not match"
                confirmpassword.requestFocus()
                return@setOnClickListener
            }
            CoroutineScope(Dispatchers.Main).launch {
                val success = userRepository.editUser(this@editprofile , email, id)
                if (success) {
                    // Registration successful, navigate to another page
                    val intent = Intent(this@editprofile, profileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
        backprofile.setOnClickListener {
            startActivity(Intent(this@editprofile,profileActivity::class.java))

            finish()
        }
    }
}