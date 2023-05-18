package com.example.khedma.view.fragments.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.khedma.R
import com.example.khedma.databinding.FragmentMyEntrepriseBinding
import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.view.activities.AddEntrepriseActivity
import com.example.khedma.view.adapters.EntrepriseRecyclerView
import com.example.khedma.view.adapters.OnListItemClick
import com.example.khedma.viewmodel.EntrepriseViewModel
import fragments.MyEntrepriseFragmentDirections
import fragments.MyOffreFragmentDirections


class my_entreprise : Fragment(R.layout.fragment_my_entreprise), OnListItemClick {
    private lateinit var binding: FragmentMyEntrepriseBinding
    private lateinit var viewModel: EntrepriseViewModel
    var restaurantsList: List<Entreprise> = emptyList()
    val restaurantRecyclerView: EntrepriseRecyclerView by lazy {
        EntrepriseRecyclerView()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyEntrepriseBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(EntrepriseViewModel::class.java)
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userRole : String? = sharedPref.getString("userRole", "")

        binding.apply {
            if(userRole=="employe") {
                tvAddRestaurant.visibility = View.GONE

            }else{tvAddRestaurant.visibility = View.VISIBLE
            }

            tvAddRestaurant.setOnClickListener {
                val intent = Intent(requireContext(), AddEntrepriseActivity::class.java)
                startActivity(intent)
            }

            recyclerView.adapter = restaurantRecyclerView
            viewModel.getEntreprises()
        }

        restaurantRecyclerView.onListItemClick = this

        binding.apply {
            viewModel.restaurantsLiveData.observe(viewLifecycleOwner,
                Observer {
                    if (it != null) {
                        restaurantRecyclerView.setList(it)
                    } else {
                        Toast.makeText(
                            context,
                            "Connection Failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            )

            viewModel.messageLiveData.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    if(it == "Server error, please try again later."){
                        Toast.makeText(
                            context,
                            it,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            })
        }
    }

    override fun onItemClick(entreprise: Entreprise) {
        val action = MyEntrepriseFragmentDirections.actionMyRestaurantsFragmentToRestaurantDetailsFragment(entreprise.id)
        findNavController().navigate(action)
    }

    override fun OnListItemClickOffre(offre: Offre) {
        val action = MyOffreFragmentDirections.actionMyOffresFragmentToOffreDetailsFragment(offre.id)
        findNavController().navigate(action)
    }

    override fun OnListItemClickCondidature(condidature: Condidature) {
        TODO("Not yet implemented")
    }


}