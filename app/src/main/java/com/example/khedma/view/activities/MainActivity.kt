package com.example.khedma.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.khedma.R
import com.example.khedma.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val navController = findNavController(R.id.mainHostFragment)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}