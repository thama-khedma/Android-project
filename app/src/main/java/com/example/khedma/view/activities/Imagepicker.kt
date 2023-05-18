package com.example.khedma.view.activities
import android.R.attr
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.khedma.R

class Imagepicker : AppCompatActivity() {
    // creating variables on below line.
    lateinit var imageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imagepicker)

        // initializing variables on below line.
        imageIV = findViewById(R.id.idIVImage)

        // adding click listener for button on below line.
            // calling intent on below line.
            val intent = Intent(MediaStore.ACTION_PICK_IMAGES)

            // starting activity on below line.
            startActivityForResult(intent, 1)


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === RESULT_OK) {
            // compare the resultCode with the
            // constant
            if (requestCode === 1) {
                // Get the url of the image from data
                val selectedImageUri: Uri = data?.data!!
                if (null != selectedImageUri) {
                    // update the image view in the layout
                    imageIV.setImageURI(selectedImageUri)
                }
            }
        }
    }
}