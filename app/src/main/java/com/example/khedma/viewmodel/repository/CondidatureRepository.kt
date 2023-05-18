package com.example.khedma.viewmodel.repository

import android.content.Context
import android.widget.Toast
import com.example.khedma.util.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody

    suspend fun AddCondidature(  entreprise: String, useremail: String, user: String, offre: String): Boolean = withContext(
        Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+ URL +":3000/condidature/Add/"
        val jsonObject = Json.encodeToString(mapOf( "entreprise" to entreprise, "useremail" to useremail, "offre" to offre, "user" to user))
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful
            val message = response.body?.string() ?: "Unknown error occurred"
        }
        success
    }
    suspend fun AcceptCondidature( id: String): Boolean = withContext(
        Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+ URL +":3000/condidature/Accept/"+id
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful
            val message = response.body?.string() ?: "Unknown error occurred"

        }
        success
    }
    suspend fun RefuseCondidature( id: String): Boolean = withContext(
        Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+ URL +":3000/condidature/Refuse/"+id
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful
            val message = response.body?.string() ?: "Unknown error occurred"

        }
        success
    }
