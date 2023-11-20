package com.github.izuum.weatherapp.data.getLocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.material.internal.ContextUtils.getActivity

const val PERMISSION_REQUEST_ACCESS_LOCATION = 100

fun GetLocation.checkPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        getActivity(context)!!,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                getActivity(context)!!,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun GetLocation.isLocationEnabled(context: Context): Boolean {
    val locationManager: LocationManager =
        getActivity(context)?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun GetLocation.requestPermission(context: Context) {
    ActivityCompat.requestPermissions(
        getActivity(context)!!,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        PERMISSION_REQUEST_ACCESS_LOCATION
    )
}