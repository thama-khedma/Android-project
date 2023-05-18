import android.content.Context
import android.util.Log
import com.example.khedma.R
import com.example.khedma.view.activities.map.Enterprise
import com.example.khedma.util.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import android.widget.Toast
import kotlinx.serialization.Serializable
@Serializable
data class EnterpriseRequest(
    val name: String?,
    val email: String?,
    val user: String?,
    val location: Location,
    val adresse: String?,
    val description: String?
)

@Serializable
data class Location(
    val type: String,
    val coordinates: List<Double>
)
suspend fun addEnterprise(
    context: Context?,
    latitude: Double,
    longitude: Double,
    userId: String?,
    name: String?,
    email: String?,
    address: String?,
    description: String?
): Boolean = withContext(Dispatchers.IO) {
    val client = OkHttpClient()
    val url = "http://"+URL+":3000/entreprise/Add"
    Log.d("TAG", "Latitude: $latitude")
    Log.d("TAG", "Longitude: $longitude")
    Log.d("TAG", "UserId: $userId")
    val location = Location("Point", listOf(longitude, latitude))
    val enterpriseRequest = EnterpriseRequest(name, email, userId, location, address, description)
    val requestBody = Json.encodeToString(enterpriseRequest).toRequestBody("application/json".toMediaType())

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










suspend fun getEnterprises(context: Context): List<Enterprise> {
    val url = "http://"+URL+":3000/entreprise/getAllEntreprise/"
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
            val enterprises = mutableListOf<Enterprise>()

            for (i in 0 until enterprisesJson.length()) {
                val enterpriseJson = enterprisesJson.getJSONObject(i)

                val id = enterpriseJson.getString("_id")
                val name = enterpriseJson.getString("name")
                val description = enterpriseJson.getString("description")
                val imageResId = R.drawable.place_singapore
                val focusZoomLvl = 15.0

                val coordinatesJson = enterpriseJson.getJSONObject("location").getJSONArray("coordinates")
                val coordinates = GeoPoint(coordinatesJson.getDouble(1), coordinatesJson.getDouble(0))

                val enterprise = Enterprise(id, name, coordinates, description, imageResId, focusZoomLvl)
                enterprises.add(enterprise)
            }
            Log.e("API_ERROR", enterprises[0].name)

            enterprises
        } else {
            Log.e("API_ERROR", "Failed to get enterprises: ${response.code}")
            listOf()
        }
    }
}
