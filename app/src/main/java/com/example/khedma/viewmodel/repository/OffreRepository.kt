package com.example.khedma.viewModel.repository
import android.content.Context;
import android.util.Log
import kotlinx.coroutines.*;
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import android.widget.Toast
import com.example.khedma.R
import com.example.khedma.util.URL
import com.example.khedma.model.entities.OffreData
import org.json.JSONArray
import org.osmdroid.util.GeoPoint

    suspend fun addOffre(context: Context, name: String?, description: String?,entreprise: String?,user: String?): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/offre/Add"
        val jsonObject = Json.encodeToString(mapOf("name" to name, "description" to description, "entreprise" to entreprise, "user" to user))
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful
            val message = response.body?.string() ?: "Unknown error occurred"
            withContext(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        success
    }
    suspend fun EditOffre(context: Context, id: String,name: String, description: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/offre/update"+id
        val jsonObject = Json.encodeToString(mapOf("name" to name, "description" to description))
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())
        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful
            val message = response.body?.string() ?: "Unknown error occurred"
            withContext(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
        success
    }

    suspend fun getOffres(context: Context): List<OffreData> {
        val url = "http://"+URL+":3000/Alloffre"
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .get()
            .build()
        return withContext(Dispatchers.IO) {
            val response = client.newCall(request).execute()
            val body = response.body?.string()
            if (response.isSuccessful && !body.isNullOrEmpty()) {
                val enterprisesJson = JSONArray(body)
                val enterprises = mutableListOf<OffreData>()
                for (i in 0 until enterprisesJson.length()) {
                    val enterpriseJson = enterprisesJson.getJSONObject(i)
                    val id = enterpriseJson.getString("_id")
                    val name = enterpriseJson.getString("name")
                    val description = enterpriseJson.getString("description")
                    val imageResId = R.drawable.place_singapore
                    val focusZoomLvl = 15.0
                    val coordinatesJson = enterpriseJson.getJSONObject("location").getJSONArray("coordinates")
                    val coordinates = GeoPoint(coordinatesJson.getDouble(1), coordinatesJson.getDouble(0))

                }

                enterprises
            } else {
                Log.e("API_ERROR", "Failed to get enterprises: ${response.code}")
                listOf()
            }
        }
    }
