package com.example.khedma.view.fragments.main

import addEnterprise
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.khedma.R
import com.example.khedma.databinding.FragmentAddEntrepriseBinding
import com.example.khedma.util.UploadRequestBody
import com.example.khedma.util.Validation
import com.example.khedma.view.activities.map.MapActivity
import com.example.khedma.viewmodel.EntrepriseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [add_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class add_entreprise : Fragment(R.layout.fragment_add_entreprise), UploadRequestBody.UploadCallback {
    private lateinit var binding: FragmentAddEntrepriseBinding
    private lateinit var viewModel: EntrepriseViewModel
    val sharedPreferences = context?.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    private val latitude = sharedPreferences?.getString("selectedLatitude", null)?.toDouble() ?: 0.0
    private val longitude = sharedPreferences?.getString("selectedLongitude", null)?.toDouble() ?: 0.0
    val userId = sharedPreferences?.getString("userId", "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddEntrepriseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntrepriseViewModel::class.java)


        binding.apply {
            buttonUpload.setOnClickListener{
                // Replace MapActivity with the name of your actual activity class
            }
            buttonAdd.setOnClickListener {

                buttonAdd.startAnimation()
                val name = edName.text.toString().trim()
                val address = edAddress.text.toString().trim()
                val description = edDescription.text.toString().trim()
                val email = edEmail.text.toString().trim()
                lifecycleScope.launch {
                    val success = addEnterprise(context, latitude,longitude,userId,name, email,address, description)
                    if (success) {
                        Toast.makeText(context, "Enterprise added successfully", Toast.LENGTH_SHORT).show()
                        buttonAdd.revertAnimation()
                    } else {
                        Toast.makeText(context, "Failed to add enterprise", Toast.LENGTH_SHORT).show()
                        buttonAdd.revertAnimation()
                    }}
            }

            lifecycleScope.launch {
                viewModel.restaurantValidation.collect { validation ->
                    edName.error = null
                    edAddress.error = null
                    edDescription.error = null
                    edEmail.error = null

                    if (validation.name is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutName.error = validation.name.message
                            buttonAdd.revertAnimation()
                        }
                    }

                    if (validation.address is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutAddress.error = validation.address.message
                            buttonAdd.revertAnimation()
                        }
                    }


                    if (validation.description is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutDescription.error = validation.description.message
                            buttonAdd.revertAnimation()
                        }
                    }

                    if (validation.email is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutPhoneNumber.error = validation.email.message
                            buttonAdd.revertAnimation()
                        }
                    }

                }
            }
        }
    }

    override fun onProgressUpdate(pecentage: Int) {

    }

}