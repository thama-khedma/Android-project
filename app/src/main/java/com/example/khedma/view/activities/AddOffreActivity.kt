package com.example.khedma.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.khedma.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.example.khedma.viewModel.repository.addOffre
class AddOffreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_offre)
        val AddBtn = findViewById<Button>(R.id.buttonSave)
        val DescriptionEditText = findViewById<EditText>(R.id.edDescription)
        val OffreTitle = findViewById<EditText>(R.id.edName)
        val sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val entreprise = "644e51f5a96ed7fea4fe369d"
        AddBtn.setOnClickListener {
            val Description = DescriptionEditText.text.toString().trim()
            val Title = OffreTitle.text.toString().trim()
            CoroutineScope(Dispatchers.Main).launch {
                val success = addOffre(this@AddOffreActivity , Title, Description,entreprise,userId)
                if (success) {
                    // Registration successful, navigate to another page
                    val intent = Intent(this@AddOffreActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }


        }
    }
}