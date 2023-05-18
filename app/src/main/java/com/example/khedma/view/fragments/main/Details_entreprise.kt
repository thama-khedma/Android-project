package com.example.khedma.view.fragments.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.khedma.R
import com.example.khedma.databinding.FragmentDetailsEntrepriseBinding
import com.example.khedma.viewmodel.EntrepriseViewModel
import fragments.EntrepriseDetailsFragmentDirections

/**
 * A simple [Fragment] subclass.
 * Use the [details_entreprise.newInstance] factory method to
 * create an instance of this fragment.
 */
class details_entreprise : Fragment(R.layout.fragment_details_entreprise) {
    private lateinit var binding: FragmentDetailsEntrepriseBinding
    private lateinit var viewModel: EntrepriseViewModel
    private lateinit var builder: AlertDialog.Builder

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsEntrepriseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntrepriseViewModel::class.java)
        builder = AlertDialog.Builder(requireContext())
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userRole : String? = sharedPref.getString("userRole", "")

        val id = arguments?.getString("restaurantId") ?: ""

        viewModel = ViewModelProvider(this).get(EntrepriseViewModel::class.java)
        viewModel.getRestaurantById(id)

        binding.apply {
            if(userRole=="employe") {
                tvDeleteEntreprise.visibility = View.GONE
                tvEditEntreprise.visibility = View.GONE

            }else{tvEditEntreprise.visibility = View.VISIBLE
                tvDeleteEntreprise.visibility = View.VISIBLE
            }
            viewModel.entrepriseLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {


                    entrepriseName.setText(it.name)
                    entrepriseDescription.setText(it.description)
                    entrepriseAddress.setText(it.adresse)

                }
            })

            tvEditEntreprise.setOnClickListener {
                val action = EntrepriseDetailsFragmentDirections.actionRestaurantDetailsFragmentToEditRestaurantFragment(id)
                findNavController().navigate(action)
            }

            tvDeleteEntreprise.setOnClickListener {
                builder.setTitle("Alert!")
                    .setMessage("Do you really want to delete this restaurant ?")
                    .setCancelable(true)
                    .setPositiveButton("Yes"){dialogInterface, it ->
                        viewModel.deleteRestaurant(id)
                        findNavController().navigate(R.id.action_restaurantDetailsFragment_to_myRestaurantsFragment)
                    }
                    .setNegativeButton("No"){dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .show()
            }

        }
    }
}