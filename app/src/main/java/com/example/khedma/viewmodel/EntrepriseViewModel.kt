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
import com.example.khedma.util.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

import java.io.IOException

class EntrepriseViewModel(application: Application) : AndroidViewModel(application) {
    //Repository
    private var repository: RepositoryImp

    //LiveData
    private var restaurantsMutableLiveData = MutableLiveData<List<Entreprise>>()
    val restaurantsLiveData: LiveData<List<Entreprise>> get() = restaurantsMutableLiveData

    private var entrepriseMutableLiveData = MutableLiveData<Entreprise>()
    val entrepriseLiveData: LiveData<Entreprise> get() = entrepriseMutableLiveData

    private var newEntrepriseMutableLiveData = MutableLiveData<Entreprise>()
    val newEntrepriseLiveData: LiveData<Entreprise> get() = newEntrepriseMutableLiveData

    private var messageMutableLiveData = MutableLiveData<String>()
    val messageLiveData: LiveData<String> get() = messageMutableLiveData

    //StateFlow
    private val _restaurantValidation = Channel<EntrepriseFieldsState>()
    val restaurantValidation = _restaurantValidation.receiveAsFlow()

    init {
        var serviceInstance = Database.getRetroBuilder().create(APIServices::class.java)
        repository = RepositoryImp(serviceInstance)
    }

    fun addRestaurant(entreprise: Entreprise) = viewModelScope.launch {
        if (checkRestaurantValidation(entreprise)) {
            try {
                val name = MultipartBody.Part.createFormData("name", entreprise.name)
                val address = MultipartBody.Part.createFormData("adresse",entreprise.adresse)
                val description = MultipartBody.Part.createFormData("description", entreprise.description)
                val email = MultipartBody.Part.createFormData("email", entreprise.email)

                var response = repository.addEntreprise(name,address,description,email)
                Log.d("TAG", "Description: ${entreprise.description}")
                if (response.isSuccessful) {
                    entrepriseMutableLiveData.postValue(response.body())
                } else {
                    if (response.code() == 400) {
                        messageMutableLiveData.postValue("Invalid information.")
                    } else {
                        messageMutableLiveData.postValue("Server error, please try again later.")
                    }
                }
                if (response.body() == null) {
                    Log.d("TAG", "Response body is empty.")
                }
            } catch (e: IOException) {
                messageMutableLiveData.postValue("Network error, please try again later.")
                Log.e("error", "IOException: ${e.message}")
            }
        } else {
            val fieldsState = EntrepriseFieldsState(
                validateEntrepriseName(entreprise.name),
                validateEntrepriseAddress(entreprise.adresse),
                validateEntrepriseDescription(entreprise.description),
                validateEntrepriseEmail(entreprise.email),

            )
            _restaurantValidation.send(fieldsState)
        }
    }

    private fun checkRestaurantValidation(entreprise: Entreprise): Boolean {
        val nameValidation = validateEntrepriseName(entreprise.name)
        val addressValidation = validateEntrepriseAddress(entreprise.adresse)
        val descriptionValidation = validateEntrepriseDescription(entreprise.description)
        val emailValidation = validateEntrepriseEmail(entreprise.email)

        val check = nameValidation is Validation.Success &&
                addressValidation is Validation.Success &&
                descriptionValidation is Validation.Success &&
                emailValidation is Validation.Success

        return check
    }

    fun getRestaurantsByUser(userId: String) = viewModelScope.launch {
        try {
            val response = repository.getEntreprisesByUser(userId)

            if (response.isSuccessful) {
                restaurantsMutableLiveData.postValue(response.body())
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }

    fun getEntreprises() = viewModelScope.launch {
        try {
            val response = repository.getEntreprises()

            if (response.isSuccessful) {
                restaurantsMutableLiveData.postValue(response.body())
            } else {
                messageMutableLiveData.postValue("Server error, please try again later.")
            }
        } catch (e: IOException) {
            messageMutableLiveData.postValue("Network error, please try again later.")
            Log.e("error", "IOException: ${e.message}")
        }
    }

    fun getRestaurantById(id: String) = viewModelScope.launch {
        try {
            val response = repository.getEntrepriseById(id)

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

    fun editRestaurant(entreprise: Entreprise) = viewModelScope.launch {
        if (checkRestaurantValidation(entreprise)) {
            try {
                var response = repository.editEntreprise(entreprise)

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
            val fieldsState = EntrepriseFieldsState(
                validateEntrepriseName(entreprise.name),
                validateEntrepriseAddress(entreprise.adresse),
                validateEntrepriseDescription(entreprise.description),
                    validateEntrepriseEmail(entreprise.email),
            )
            _restaurantValidation.send(fieldsState)
        }
    }

    fun deleteRestaurant(id: String) = viewModelScope.launch {
        try {
            val response = repository.deleteEntreprise(id)

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