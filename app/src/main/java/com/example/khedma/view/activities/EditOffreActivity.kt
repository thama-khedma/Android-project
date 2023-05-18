package com.example.khedma.view.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.khedma.R
import com.example.khedma.viewModel.repository.EditOffre
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditOffreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_offre)
        val AddBtn = findViewById<Button>(R.id.AddBtn)
        val DescriptionEditText = findViewById<EditText>(R.id.DescriptionEditText)
        val OffreTitle = findViewById<EditText>(R.id.OffreTitle)
        val sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val id = "64543cfa0ef55ef2677e6786"
        val entreprise = "644e51f5a96ed7fea4fe369d"
        AddBtn.setOnClickListener {
            val Description = DescriptionEditText.text.toString().trim()
            val Title = OffreTitle.text.toString().trim()

            CoroutineScope(Dispatchers.Main).launch {
                val success = EditOffre(this@EditOffreActivity ,id, Title, Description)
                if (success) {
                    val intent = Intent(this@EditOffreActivity, profileActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }


        }
    }
}