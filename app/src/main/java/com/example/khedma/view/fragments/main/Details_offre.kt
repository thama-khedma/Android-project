package com.example.khedma.view.fragments.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.khedma.R
import com.example.khedma.databinding.FragmentDetailsEntrepriseBinding
import com.example.khedma.databinding.FragmentDetailsOffreBinding
import com.example.khedma.model.entities.Offre
import com.example.khedma.viewmodel.EntrepriseViewModel
import com.example.khedma.viewmodel.OffreViewModel
import com.example.khedma.viewmodel.repository.AddCondidature
import fragments.EntrepriseDetailsFragmentDirections
import fragments.OffreDetailsFragmentDirections
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [details_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class details_offre : Fragment(R.layout.fragment_details_offre) {
    private lateinit var binding: FragmentDetailsOffreBinding
    private lateinit var viewModel: OffreViewModel
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsOffreBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OffreViewModel::class.java)
        builder = AlertDialog.Builder(requireContext())
        val sharedPrefs = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val id = arguments?.getString("offreId") ?: ""
        val offre = sharedPrefs.getString("offre", "") ?: ""
        val user = sharedPrefs.getString("user", "testuser") ?: "testuser"
        val userEmail = sharedPrefs.getString("userEmail", "") ?: ""
        val entreprise = sharedPrefs.getString("entreprise", "entreprisetest") ?: "entreprisetest"
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userRole : String? = sharedPref.getString("userRole", "")

        viewModel = ViewModelProvider(this).get(OffreViewModel::class.java)
        viewModel.getOffreById(id)

        binding.apply {
            if(userRole=="employe") {
                tvDeleteOffre.visibility = View.GONE
                tvEditOffre.visibility = View.GONE

            }else{tvEditOffre.visibility = View.VISIBLE
                tvDeleteOffre.visibility = View.VISIBLE
            }
            viewModel.entrepriseLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {


                    offreName.setText(it.name)
                    OffreDescription.setText(it.description)


                }
            })

            tvEditOffre.setOnClickListener {
                val action = OffreDetailsFragmentDirections.actionOffreDetailsFragmentToEditOffreFragment(id)
                findNavController().navigate(action)
            }
            applyButton.setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                 val succes = AddCondidature(entreprise,userEmail,user,offre)
                    if (succes) {
                        Toast.makeText(
                            context,
                            "Added succesfully",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

            }
            tvDeleteOffre.setOnClickListener {
                builder.setTitle("Alert!")
                    .setMessage("Do you really want to delete this ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes"){dialogInterface, it ->
                        viewModel.deleteOffre(id)
                        findNavController().navigate(R.id.action_offerDetailsFragment_to_myOffersFragment)
                    }
                    .setNegativeButton("No"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }

        }
    }
}