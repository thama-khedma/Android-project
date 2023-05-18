package com.example.khedma.view.activities.map

import android.content.Context
import getEnterprises
import org.osmdroid.util.GeoPoint

data class Enterprise (
    val id:String,
    val name:String,
    val coordinates: GeoPoint,
    val description:String,
    val imageResId: Int,
    val focusZoomLvl:Double
)

var Enterprises: MutableList<Enterprise> = mutableListOf()

suspend fun fillPlaces(context: Context) {

        val enterprises = getEnterprises(context)
        Enterprises.addAll(enterprises)

        // do something with the list of enterprises here

}

