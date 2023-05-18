package com.example.khedma.view.fragments.main

import android.content.Context
import android.content.Intent
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
import com.example.khedma.databinding.FragmentMyOffreBinding
import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.view.activities.AddEntrepriseActivity
import com.example.khedma.view.activities.AddOffreActivity
import com.example.khedma.view.adapters.OffreRecyclerView
import com.example.khedma.view.adapters.OnListItemClick
import com.example.khedma.viewmodel.OffreViewModel
import fragments.MyEntrepriseFragmentDirections
import fragments.MyOffreFragmentDirections

/**
 * A simple [Fragment] subclass.
 * Use the [my_offre.newInstance] factory method to
 * create an instance of this fragment.
 */
class my_offre  : Fragment(R.layout.fragment_my_offre), OnListItemClick {
    private lateinit var binding: FragmentMyOffreBinding
    private lateinit var viewModel: OffreViewModel
    var restaurantsList: List<Offre> = emptyList()
    val restaurantRecyclerView: OffreRecyclerView by lazy {
        OffreRecyclerView()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyOffreBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(OffreViewModel::class.java)
        //val sharedPref = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        //  val userId : String = sharedPref.getString("id", "") ?: ""
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val userRole : String? = sharedPref.getString("userRole", "")

        binding.apply {
            tvAddOffre.setOnClickListener {
                val intent = Intent(requireContext(), AddOffreActivity::class.java)
                startActivity(intent)
            }
            if(userRole=="employe") {
                tvAddOffre.visibility = View.GONE

            }else{tvAddOffre.visibility = View.VISIBLE
                 }
            recyclerView.adapter = restaurantRecyclerView
            viewModel.getOffre()
        }

        restaurantRecyclerView.onListItemClick = this

        binding.apply {
            viewModel.offreLiveData.observe(viewLifecycleOwner,
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