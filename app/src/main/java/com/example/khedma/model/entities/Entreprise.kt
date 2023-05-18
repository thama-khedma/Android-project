package com.example.khedma.model.entities

import com.google.gson.annotations.SerializedName

data class Entreprise(
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("adresse")
    var adresse: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("description")
    var description: String,

)

