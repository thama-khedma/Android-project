package com.example.khedma.view.fragments.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.khedma.R
import com.example.khedma.databinding.FragmentEditEntrepriseBinding
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.util.Validation
import com.example.khedma.viewmodel.EntrepriseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [edit_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class edit_entreprise: Fragment(R.layout.fragment_edit_entreprise) {
    private lateinit var binding: FragmentEditEntrepriseBinding
    private lateinit var viewModel: EntrepriseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditEntrepriseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntrepriseViewModel::class.java)
        val id = arguments?.getString("restaurantId") ?: ""
        //val sharedPref = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
       // var userId : String = sharedPref.getString("id", "") ?: ""

        viewModel.getRestaurantById(id)

        binding.apply {
            viewModel.entrepriseLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    edName.setText(it.name)
                    edDescription.setText(it.description)
                    edAddress.setText(it.adresse)
                    edEmail.setText(it.email)
                }
            })

            buttonSave.setOnClickListener {
                buttonSave.startAnimation()
                val restaurant = Entreprise(
                    id,
                    edName.text.toString().trim(),
                    edAddress.text.toString().trim(),
                    edDescription.text.toString().trim(),
                    edEmail.text.toString().trim(),


                )

                viewModel.editRestaurant(restaurant)
            }

            viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    buttonSave.revertAnimation()
                    Toast.makeText(
                        context,
                        it,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

            lifecycleScope.launch {
                viewModel.restaurantValidation.collect { validation ->
                    edName.error = null
                    edAddress.error = null
                    edDescription.error = null

                    if (validation.name is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutName.error = validation.name.message
                            buttonSave.revertAnimation()
                        }
                    }

                    if (validation.address is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutAddress.error = validation.address.message
                            buttonSave.revertAnimation()
                        }
                    }


                    if (validation.description is Validation.Failed) {
                        withContext(Dispatchers.Main) {
                            layoutDescription.error = validation.description.message
                            buttonSave.revertAnimation()
                        }
                    }

                }
            }

        }
    }
}