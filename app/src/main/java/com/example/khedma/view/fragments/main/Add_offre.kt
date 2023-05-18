package com.example.khedma.view.fragments.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.khedma.R
import com.example.khedma.databinding.FragmentAddEntrepriseBinding
import com.example.khedma.databinding.FragmentAddOffreBinding
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.util.UploadRequestBody
import com.example.khedma.util.Validation
import com.example.khedma.util.getFileName
import com.example.khedma.viewmodel.EntrepriseViewModel
import com.example.khedma.viewmodel.OffreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

/**
 * A simple [Fragment] subclass.
 * Use the [add_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class add_offre : Fragment(R.layout.fragment_add_offre){
    private lateinit var binding: FragmentAddOffreBinding
    private lateinit var viewModel: OffreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddOffreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OffreViewModel::class.java)
        // val contentResolver = requireContext().contentResolver
        //val sharedPref = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
       // val userId : String = sharedPref.getString("id", "") ?: ""

        binding.apply {
            buttonSave.setOnClickListener {
                buttonSave.startAnimation()
                val offre = Offre(
                    "0",
                    edName.text.toString().trim(),
                    edDescription.text.toString().trim(),


                )
                viewModel.addOffre(offre)
            }

            viewModel.entrepriseLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    buttonSave.revertAnimation()

                    Toast.makeText(
                        context,
                        "Offre added successfully.",
                        Toast.LENGTH_LONG
                    ).show()

                    edName.setText("")
                    edDescription.setText("")

                }
            })

            viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    buttonSave.revertAnimation()

                    if(it == "Server error, please try again later."){
                        Toast.makeText(
                            context,
                            it,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })

            lifecycleScope.launch {
                viewModel.restaurantValidation.collect { validation ->
                    edName.error = null
                    edDescription.error = null


                    if (validation.name is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutName.error = validation.name.message
                            buttonSave.revertAnimation()
                        }
                    }

                    if (validation.address is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            buttonSave.revertAnimation()
                        }
                    }






                }
            }
        }
    }



}