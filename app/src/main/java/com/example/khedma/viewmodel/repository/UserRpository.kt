package com.example.khedma.viewmodel.repository;
import android.content.Context;
import kotlinx.coroutines.*;
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import android.widget.Toast
import com.example.khedma.util.URL

class UserRepository {
    // Register user with email and password
    suspend fun registerUser(context: Context, email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/user/compte"
        val jsonObject = Json.encodeToString(mapOf("email" to email, "password" to password))
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
    suspend fun loginUser(context: Context, email: String, password: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()

        val url = "http://"+URL+":3000/user/Login"

        val jsonObject = Json.encodeToString(mapOf("email" to email, "password" to password))

        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaType())

        val request = Request.Builder()
            .url(url)
            .post(requestBody)
            .build()
        var success = false
        client.newCall(request).execute().use { response ->
            success = response.isSuccessful

        }
        client.newCall(request).enqueue(object: Callback{
            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val jsonResponse = JSONObject(responseBody)
                        val userId = jsonResponse.getString("_id")
                        val userEmail = jsonResponse.getString("email")
                        val userRole = jsonResponse.getString("Role")

                        // parse other fields as needed
                        // ...
                        println("test" + userId + userEmail)
                        val sharedPreferences = context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("userId", userId)
                        editor.putString("userEmail", userEmail)
                        editor.putString("userRole", userRole)
                        editor.apply()
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
            }
        })

        success
    }

    suspend fun editUser(context: Context, email: String, id: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/user/updateUser/"+id
        val jsonObject = Json.encodeToString(mapOf("email" to email))
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

    suspend fun forgetpassword(context: Context, email: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/user/resetpwd"
        val jsonObject = Json.encodeToString(mapOf("email" to email))
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
    suspend fun rstpassword(context: Context, code: String? ,password: String): Boolean = withContext(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "http://"+URL+":3000/user/resetpassword"
        val jsonObject = Json.encodeToString(mapOf("code" to code, "password" to password))
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
/**///resetpassword

    private val client = OkHttpClient()
    suspend fun run() = withContext(Dispatchers.IO) {
        val jsonBody = Json.encodeToString(mapOf("email" to "azraze@gmail.com", "password" to "aaaaaa"))

        val request = Request.Builder()
            .url("http://172.20.10.2:3000/user/compte")
            .post(jsonBody.toString().toRequestBody("application/json".toMediaType()))
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            println(response.body!!.string())
        }
    }

    companion object {
        val MEDIA_TYPE_MARKDOWN = "text/x-markdown; charset=utf-8".toMediaType()
    }


}

