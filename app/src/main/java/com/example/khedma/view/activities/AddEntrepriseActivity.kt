package com.example.khedma.view.activities

import addEnterprise
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.khedma.R
import com.example.khedma.view.activities.map.MapActivity
import kotlinx.coroutines.launch

class AddEntrepriseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_entreprise)
        val edName = findViewById<TextView>(R.id.edName)
        val edAddress = findViewById<TextView>(R.id.edAddress)
        val edDescription = findViewById<TextView>(R.id.edDescription)
        val edEmail = findViewById<TextView>(R.id.edEmail)
        val buttonAdd = findViewById<TextView>(R.id.buttonAdd)
        val buttonUpload = findViewById<TextView>(R.id.buttonUpload)

        val name = edName.text.toString().trim()
        val address = edAddress.text.toString().trim()
        val description = edDescription.text.toString().trim()
        val email = edEmail.text.toString().trim()
        val sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val latitude = sharedPreferences?.getFloat("selectedLatitude", 0.0f)?.toDouble() ?: 0.0
        val longitude = sharedPreferences?.getFloat("selectedLongitude", 0.0f)?.toDouble() ?: 0.0
        val userId = sharedPreferences?.getString("userId", "")
        buttonUpload.setOnClickListener{
            val intent = Intent(this@AddEntrepriseActivity, MapActivity::class.java)
            startActivity(intent)
            finish()        }
        buttonAdd.setOnClickListener {
            val name = edName.text.toString().trim()
            val address = edAddress.text.toString().trim()
            val description = edDescription.text.toString().trim()
            val email = edEmail.text.toString().trim()
            lifecycleScope.launch {
                val success = addEnterprise(this@AddEntrepriseActivity, latitude,longitude,userId,name, email,address, description)
                if (success) {
                    Toast.makeText(this@AddEntrepriseActivity, "Enterprise added successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@AddEntrepriseActivity, "Failed to add enterprise", Toast.LENGTH_SHORT).show()
                }}
        }
    }
}