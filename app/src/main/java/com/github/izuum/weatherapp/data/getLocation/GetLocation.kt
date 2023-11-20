package com.github.izuum.weatherapp.data.getLocation

import android.content.Context
import android.content.Intent
import android.location.Location
import android.provider.Settings
import android.widget.Toast
import com.github.izuum.weatherapp.data.liveData.coordinates
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.internal.ContextUtils.getActivity

class GetLocation(private val context: Context) {

    private var fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocation() {
        if (checkPermission(context)) {
            if (isLocationEnabled(context)) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(getActivity(context)!!) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(getActivity(context), "Null Received", Toast.LENGTH_SHORT).show()
                    } else {
                        coordinates.value = ("${location.latitude},${location.longitude}")
                    }
                }
            } else {
                Toast.makeText(getActivity(context), "Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                getActivity(context)?.startActivity(intent)
            }
        } else {
            requestPermission(context)
        }
    }
}
