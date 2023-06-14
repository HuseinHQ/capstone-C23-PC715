package com.example.lapakkita_android.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import com.example.lapakkita_android.R
import java.io.IOException
import java.util.*

fun getLocationFromLatLng(lat: Double, lng: Double, context: Context): String {
    var location = ""
    val geocoder = Geocoder(
        context,
        Locale.getDefault()
    )
    try {
        val list = geocoder.getFromLocation(
            lat,
            lng,
            1
        )
        if (list != null && list.size != 0) {
            location = list[0].subLocality + ", " + list[0].locality
        }
    } catch (e: IOException) {
        e.printStackTrace()
        Log.d(
            "ERR",
            context.resources.getString(R.string.connection_error),
        )
    }

    return location
}