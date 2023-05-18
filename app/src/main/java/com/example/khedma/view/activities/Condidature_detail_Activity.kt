package com.example.khedma.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.khedma.R
import com.example.khedma.viewmodel.repository.AcceptCondidature
import com.example.khedma.viewmodel.repository.RefuseCondidature
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Condidature_detail_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_condidature_detail)
        val Accept = findViewById<Button>(R.id.Accept)
        val Refuse = findViewById<Button>(R.id.Refuse)
        val userId = "644ce9e8fb679ae7083ca54c"

        Accept.setOnClickListener {

        }

        Refuse.setOnClickListener {

        }
    }
}