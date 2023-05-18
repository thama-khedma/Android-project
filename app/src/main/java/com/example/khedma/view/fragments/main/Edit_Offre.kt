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
import com.example.khedma.databinding.FragmentEditOffreBinding
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.util.Validation
import com.example.khedma.viewmodel.EntrepriseViewModel
import com.example.khedma.viewmodel.OffreViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A simple [Fragment] subclass.
 * Use the [edit_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class edit_Offre: Fragment(R.layout.fragment_edit_offre) {
    private lateinit var binding: FragmentEditOffreBinding
    private lateinit var viewModel: OffreViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditOffreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OffreViewModel::class.java)
        val id = arguments?.getString("offreId") ?: ""
        //val sharedPref = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        // var userId : String = sharedPref.getString("id", "") ?: ""

        viewModel.getOffreById(id)

        binding.apply {
            viewModel.entrepriseLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    edName.setText(it.name)
                    edDescription.setText(it.description)

                }
            })

            buttonSave.setOnClickListener {
                buttonSave.startAnimation()
                val offre = Offre(
                    id,
                    edName.text.toString().trim(),

                    edDescription.text.toString().trim(),



                    )

                viewModel.editOffre(offre)
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