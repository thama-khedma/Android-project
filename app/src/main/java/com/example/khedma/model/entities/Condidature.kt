package com.example.khedma.model.entities

import com.google.gson.annotations.SerializedName

data class Condidature(
    @SerializedName("_id")
    var id: String,
    @SerializedName("user")
    var user: String,
    @SerializedName("offre")
    var offre: String,
    @SerializedName("useremail")
    var useremail: String,
    @SerializedName("entreprise")
    var entreprise: String,



    )

