package com.example.myapplication.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.myapplication.R
import android.content.Context
import android.content.Intent
import android.widget.ImageView
import android.widget.Button
import com.google.android.material.bottomsheet.BottomSheetDialog

class profileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val btnShowBottomSheet = findViewById<ImageView>(R.id.botom_sheet);

        val modifyprofile = findViewById<ImageView>(R.id.modifyprofile)
        val sharedPreferences = this.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences.getString("userId", "")
        val userEmail = sharedPreferences.getString("userEmail", "")
        val ProfileEmailTextView = findViewById<TextView>(R.id.ProfileEmail)
        ProfileEmailTextView.text = userEmail
        // adding on click listener for our button.
        btnShowBottomSheet.setOnClickListener {
            // on below line we are creating a new bottom sheet dialog.
            val dialog = BottomSheetDialog(this)
            // on below line we are inflating a layout file which we have created.
            val view = layoutInflater.inflate(R.layout.bottom_sheet_dialog, null)
            // on below line we are creating a variable for our button
            // which we are using to dismiss our dialog.
            val btnEdit = view.findViewById<TextView>(R.id.layoutEdit)
            // on below line we are adding on click listener
            // for our dismissing the dialog button.
            btnEdit.setOnClickListener {
                // on below line we are calling a dismiss
                // method to close our dialog.
                val intent = Intent(this@profileActivity, editprofile::class.java)
                startActivity(intent)
                finish()
                dialog.dismiss()
            }
            // below line is use to set cancelable to avoid
            // closing of dialog box when clicking on the screen.
            dialog.setCancelable(false)
            // on below line we are setting
            // content view to our view.
            dialog.setContentView(view)
            // on below line we are calling
            // a show method to display a dialog.
            dialog.show()
        }
        //botom_sheet

    }
}