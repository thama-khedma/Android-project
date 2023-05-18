package com.example.khedma.model.entities

import com.google.gson.annotations.SerializedName

data class Offre(
    @SerializedName("_id")
    var id: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("description")
    var description: String,

)

