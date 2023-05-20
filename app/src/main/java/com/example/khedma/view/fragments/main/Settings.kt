package com.example.khedma.view.fragments.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.fragment.app.Fragment
import com.example.khedma.R
import com.example.khedma.databinding.FragmentSettingsBinding
import com.example.khedma.view.activities.MainActivity
import androidx.navigation.fragment.findNavController
import com.example.khedma.view.activities.AddOffreActivity
import com.example.khedma.view.activities.Singin_up_Activity
import com.example.khedma.view.activities.map.MapActivity
import com.example.khedma.view.activities.profileActivity

/**
 * A simple [Fragment] subclass.
 * Use the [Settings.newInstance] factory method to
 * create an instance of this fragment.
 */
class Settings : Fragment(R.layout.fragment_settings){

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
        val fullname : String? = sharedPref.getString("userEmail", "")
        val userRole : String? = sharedPref.getString("userRole", "")

        binding.apply {
            constraintProfile.setOnClickListener {
                val intent = Intent(requireContext(), profileActivity::class.java)
                startActivity(intent)
            }

            tvFullName.text = fullname
            if(userRole=="employe") {
                tvSecurity.visibility = View.GONE
                linearChangePassword.visibility = View.GONE
            }else{tvSecurity.visibility = View.VISIBLE
                linearChangePassword.visibility = View.VISIBLE }
            linearLogout.setOnClickListener {
                val sharedPref = requireContext().getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear()
                editor.apply()
                val intent = Intent(requireActivity(), Singin_up_Activity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            tvAllOrders.setOnClickListener {
                val intent = Intent(requireContext(), MapActivity::class.java)
                startActivity(intent)
            }
            linearChangePassword.setOnClickListener {
                findNavController().navigate(R.id.action_settingsFragment_to_changePasswordFragment)

            }

        }
    }
}