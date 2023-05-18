package com.example.khedma.view.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.khedma.R
import com.example.khedma.databinding.FragmentMyCondidatureBinding
import com.example.khedma.databinding.FragmentMyOffreBinding
import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.view.adapters.CondidatureRecyclerView
import com.example.khedma.view.adapters.OffreRecyclerView
import com.example.khedma.view.adapters.OnListItemClick
import com.example.khedma.viewmodel.CondidatureViewModel
import com.example.khedma.viewmodel.OffreViewModel
import fragments.MyEntrepriseFragmentDirections
import fragments.MyOffreFragmentDirections

/**
 * A simple [Fragment] subclass.
 * Use the [my_offre.newInstance] factory method to
 * create an instance of this fragment.
 */
class my_condidature  : Fragment(R.layout.fragment_my_condidature), OnListItemClick {
    private lateinit var binding: FragmentMyCondidatureBinding
    private lateinit var viewModel: CondidatureViewModel
    var restaurantsList: List<Condidature> = emptyList()
    val restaurantRecyclerView: CondidatureRecyclerView by lazy {
        CondidatureRecyclerView()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCondidatureBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(CondidatureViewModel::class.java)
        //val sharedPref = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
      //  val userId : String = sharedPref.getString("id", "") ?: ""

        binding.apply {
            recyclerView.adapter = restaurantRecyclerView
            viewModel.getCondidature()
        }

        restaurantRecyclerView.onListItemClick = this

        binding.apply {
            viewModel.condidatureLiveData.observe(viewLifecycleOwner,
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