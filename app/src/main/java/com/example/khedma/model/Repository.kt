package com.example.khedma.model

import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import okhttp3.MultipartBody

import retrofit2.Response




interface Repository {

    suspend fun addEntreprise(name: MultipartBody.Part, adresse: MultipartBody.Part, description: MultipartBody.Part,email: MultipartBody.Part): Response<Entreprise>
    suspend fun getEntreprisesByUser(userId: String): Response<List<Entreprise>>
    suspend fun getEntrepriseById(id: String): Response<Entreprise>
    suspend fun editEntreprise(entreprise: Entreprise): Response<Entreprise>
    suspend fun deleteEntreprise(id: String): Response<Entreprise>
    suspend fun getEntreprises() : Response<List<Entreprise>>



    suspend fun addOffre(name: MultipartBody.Part, description: MultipartBody.Part): Response<Offre>
    suspend fun editOffre(offre: Offre): Response<Offre>
    suspend fun deleteOffre(id: String): Response<Offre>
    suspend fun getOffre() : Response<List<Offre>>
    suspend fun getOffreById(id: String): Response<Offre>

    suspend fun getCondidature() : Response<List<Condidature>>

}