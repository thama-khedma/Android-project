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
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import com.example.khedma.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

import java.io.IOException

class OffreViewModel(application: Application) : AndroidViewModel(application) {
    //Repository
    private var repository: RepositoryImp

    //LiveData
    private var offreMutableLiveData = MutableLiveData<List<Offre>>()
    val offreLiveData: LiveData<List<Offre>> get() = offreMutableLiveData

    private var entrepriseMutableLiveData = MutableLiveData<Offre>()
    val entrepriseLiveData: LiveData<Offre> get() = entrepriseMutableLiveData

    private var newEntrepriseMutableLiveData = MutableLiveData<Offre>()
    val newEntrepriseLiveData: LiveData<Offre> get() = newEntrepriseMutableLiveData

    private var messageMutableLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = messageMutableLiveData

    //StateFlow
    private val _restaurantValidation = Channel<OffreFieldsState>()
    val restaurantValidation = _restaurantValidation.receiveAsFlow()

    init {
        var serviceInstance = Database.getRetroBuilder().create(APIServices::class.java)
        repository = RepositoryImp(serviceInstance)
    }


    fun addOffre(offre: Offre) = viewModelScope.launch {
        if (checkOffreValidation(offre)) {
            try {
                val name = MultipartBody.Part.createFormData("name", offre.name)
                val address = MultipartBody.Part.createFormData("adresse",offre.description)




                var response = repository.addOffre(name,address)

                if (response.isSuccessful) {
                    entrepriseMutableLiveData.postValue(response.body())
                } else {
                    if (response.code() == 400) {
                        messageMutableLiveData.postValue("Invalid information.")
                    } else {
                        messageMutableLiveData.postValue("Server error, please try again later.")
                    }
                }
            } catch (e: IOException) {
                messageMutableLiveData.postValue("Network error, please try again later.")
                Log.e("error", "IOException: ${e.message}")
            }
        } else {
            val fieldsState = OffreFieldsState(
                validateOffreName(offre.name),
                validateOffreDescription(offre.description),


                )
            _restaurantValidation.send(fieldsState)
        }
    }


    private fun checkOffreValidation(offre: Offre): Boolean {
        val nameValidation = validateOffreName(offre.name)
        val descriptionValidation = validateOffreDescription(offre.description)
        val check = nameValidation is Validation.Success &&
                descriptionValidation is Validation.Success


        return check
    }



    fun getOffre() = viewModelScope.launch {
        try {
            val response = repository.getOffre()

            if (response.isSuccessful) {
                offreMutableLiveData.postValue(response.body())
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }

    fun getOffreById(id: String) = viewModelScope.launch {
        try {
            val response = repository.getOffreById(id)

            if (response.isSuccessful) {
                entrepriseMutableLiveData.postValue(response.body())
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }

    fun editOffre(offre: Offre) = viewModelScope.launch {
        if (checkOffreValidation(offre)) {
            try {
                var response = repository.editOffre(offre)

                if (response.isSuccessful) {
                    messageMutableLiveData.postValue("Entreprise Edited Successfully.")
                } else {
                    if (response.code() == 400) {
                        messageMutableLiveData.postValue("Invalid information.")
                    } else {
                        messageMutableLiveData.postValue("Server error, please try again later.")
                    }
                }
            } catch (e: IOException) {
                messageMutableLiveData.postValue("Network error, please try again later.")
                Log.e("error", "IOException: ${e.message}")
            }
        } else {
            val fieldsState = OffreFieldsState(
                validateOffreName(offre.name),
                validateOffreDescription(offre.description),

                )

            _restaurantValidation.send(fieldsState)
        }
    }

    fun deleteOffre(id: String) = viewModelScope.launch {
        try {
            val response = repository.deleteOffre(id)

            if (response.isSuccessful) {
                messageMutableLiveData.postValue("Entreprise deleted successfully.")
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }

}
