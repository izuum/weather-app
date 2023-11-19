package com.github.izuum.weatherapp.data.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.github.izuum.weatherapp.presenter.fragments.MainFragment

fun Fragment.checkPermission(): Boolean {
    return ActivityCompat.checkSelfPermission(
        activity as AppCompatActivity,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                activity as AppCompatActivity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

fun Fragment.isLocationEnabled(): Boolean {
    val locationManager: LocationManager =
        activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

fun Fragment.requestPermission() {
    ActivityCompat.requestPermissions(
        activity as AppCompatActivity,
        arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ),
        MainFragment.PERMISSION_REQUEST_ACCESS_LOCATION
    )
}