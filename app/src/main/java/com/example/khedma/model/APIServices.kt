package com.example.khedma.model

import com.example.khedma.model.entities.Condidature
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import com.example.khedma.model.entities.Entreprise
import com.example.khedma.model.entities.Offre


interface APIServices {



    @Multipart
    @POST("/entreprise/Add")
    suspend fun addEntreprise(@Part name: MultipartBody.Part,
                              @Part address: MultipartBody.Part,
                              @Part description: MultipartBody.Part,
                              @Part email: MultipartBody.Part,): Response<Entreprise>

    @GET("/restaurants/getByUser/{userId}")
    suspend fun getEntreprisesByUser(@Path("userId") userId: String): Response<List<Entreprise>>
    @GET("/entreprise/getAllEntreprise/")
    suspend fun getEntreprises(): Response<List<Entreprise>>
    @GET("/entreprise/GetEntreprise/{id}")
    suspend fun getEntrepriseById(@Path("id") id: String): Response<Entreprise>
    @PUT("/entreprise/update/{id}")
    suspend fun editEntreprise(@Path("id") id: String, @Body entreprise: Entreprise): Response<Entreprise>
    @DELETE("/entreprise/delete/{id}")
    suspend fun deleteEntreprise(@Path("id") id: String): Response<Entreprise>

    @Multipart
    @POST("/offre/Add")
    suspend fun addOffre(@Part name: MultipartBody.Part,
                         @Part description: MultipartBody.Part,
    ): Response<Offre>
    @GET("/offre/getAllOffre/")
    suspend fun getOffre(): Response<List<Offre>>
    @PUT("/offre/update/{id}")
    suspend fun editOffre(@Path("id") id: String, @Body offre: Offre): Response<Offre>
    @DELETE("/offre/delete/{id}")
    suspend fun deleteOffre(@Path("id") id: String): Response<Offre>
    @GET("/offre/search/{id}")
    suspend fun getOffreByEntreprise(@Path("id") id: String): Response<Offre>
    @GET("/offre/GetOffre/{id}")
    suspend fun getOffreById(@Path("id") id: String): Response<Offre>

    @GET("/condidature/getAllCondidature")
    suspend fun getCondidature(): Response<List<Condidature>>

}