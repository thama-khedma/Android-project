package com.example.khedma.model

import com.example.khedma.model.entities.Condidature
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import retrofit2.Response


class RepositoryImp(private val api: APIServices) : Repository {
    override suspend fun addEntreprise(
        name: MultipartBody.Part,
        adresse: MultipartBody.Part,
        description: MultipartBody.Part,
        email: MultipartBody.Part,
    ): Response<Entreprise> = withContext(Dispatchers.IO){
        api.addEntreprise(name,description,adresse,email)
    }


    override suspend fun getEntreprisesByUser(userId: String): Response<List<Entreprise>>  = withContext(Dispatchers.IO) {
        api.getEntreprisesByUser(userId)
    }

    override suspend fun getEntrepriseById(id: String): Response<Entreprise>  = withContext(Dispatchers.IO) {
        api.getEntrepriseById(id)
    }

    override suspend fun editEntreprise(entreprise: Entreprise): Response<Entreprise>  = withContext(Dispatchers.IO) {
        api.editEntreprise(entreprise.id , entreprise)
    }

    override suspend fun deleteEntreprise(id: String): Response<Entreprise>  = withContext(Dispatchers.IO){
        api.deleteEntreprise(id)
    }

    override suspend fun getEntreprises(): Response<List<Entreprise>>  = withContext(Dispatchers.IO){
        api.getEntreprises()
    }

    override suspend fun addOffre(
        name: MultipartBody.Part,
        description: MultipartBody.Part
    ): Response<Offre> = withContext(Dispatchers.IO){
        api.addOffre(name,description)
    }

    override suspend fun editOffre(offre: Offre): Response<Offre> = withContext(Dispatchers.IO){
        api.editOffre(offre.id , offre)
    }

    override suspend fun deleteOffre(id: String): Response<Offre> = withContext(Dispatchers.IO){
        api.deleteOffre(id)
    }

    override suspend fun getOffre(): Response<List<Offre>> = withContext(Dispatchers.IO) {
        api.getOffre()
    }

    override suspend fun getOffreById(id: String): Response<Offre>  = withContext(Dispatchers.IO){
        api.getOffreById(id)
    }

    override suspend fun getCondidature(): Response<List<Condidature>>   = withContext(Dispatchers.IO) {
        api.getCondidature()
    }


}