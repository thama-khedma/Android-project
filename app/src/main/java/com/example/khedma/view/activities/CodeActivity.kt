package com.example.khedma.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

import com.example.khedma.R

class CodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)

        val CodeBtn = findViewById<Button>(R.id.CodeBtn)
        val CodeEditText = findViewById<EditText>(R.id.CodeEditText)

        CodeBtn.setOnClickListener {
            val code = CodeEditText.text.toString().trim()
            val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString("code", code)
            editor.apply()
                    val intent = Intent(this@CodeActivity, rstpsswrd_activity::class.java)
                    startActivity(intent)
                    finish()

        }

    }
}