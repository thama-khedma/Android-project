package com.example.khedma.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.khedma.model.APIServices
import com.example.khedma.model.Database
import com.example.khedma.model.RepositoryImp
import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Offre
import com.example.khedma.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


import java.io.IOException

class CondidatureViewModel(application: Application) : AndroidViewModel(application) {
    //Repository
    private var repository: RepositoryImp

    //LiveData
    private var condidatureMutableLiveData = MutableLiveData<List<Condidature>>()
    val condidatureLiveData: LiveData<List<Condidature>> get() = condidatureMutableLiveData

    private var offreMutableLiveData = MutableLiveData<Condidature>()
    val condidatureItemLiveData: LiveData<Condidature> get() = offreMutableLiveData

    private var newCondidatureMutableLiveData = MutableLiveData<Condidature>()
    val newEntrepriseLiveData: LiveData<Condidature> get() = newCondidatureMutableLiveData

    private var messageMutableLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = messageMutableLiveData


    init {
        var serviceInstance = Database.getRetroBuilder().create(APIServices::class.java)
        repository = RepositoryImp(serviceInstance)
    }



    fun getCondidature() = viewModelScope.launch {
        try {
            val response = repository.getCondidature()

            if (response.isSuccessful) {
                condidatureMutableLiveData.postValue(response.body())
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }





}
