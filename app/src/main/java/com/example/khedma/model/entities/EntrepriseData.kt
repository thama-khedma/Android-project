package com.example.khedma.model.entities

import org.osmdroid.util.GeoPoint

data class Enterprise (
    val id:String,
    val name:String,
    val coordinates: GeoPoint,
    val description:String,
    val imageResId: Int,
    val focusZoomLvl:Double
)