package com.github.izuum.weatherapp.fragments

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import com.github.izuum.weatherapp.MainViewModel
import com.github.izuum.weatherapp.databinding.FragmentMainBinding
import com.github.izuum.weatherapp.retorfit.MainApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val API_KEY = "804967b7a2934d2a965221051232602"


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var nameOfCity: String = ""
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

        nameOfCity = getCurrentLocation()

        swipeFun()
    }


    private fun init() {
        handler = Handler()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

    }

    private fun swipeFun() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            runnable = Runnable {
                nameOfCity = getCurrentLocation()

                getWeather()

                updateWeatherInfo()
                binding.swipeRefreshLayout.isRefreshing = false
            }
            handler.postDelayed(runnable, 500.toLong())
        }
        binding.swipeRefreshLayout.setColorSchemeColors(
            Color.BLUE,
            Color.CYAN,
            Color.GREEN,
            Color.RED
        )
    }


    private fun updateWeatherInfo() = with(binding) {
        model.liveDataCurrent.observe(viewLifecycleOwner) {
            tvCityName.text = it.location.name
            tvTemp.text = it.current.temp_c.toString()
            tvDescription.text = it.current.condition.text
            tvLastUpdate.text = it.current.last_updated
        }
    }

    private fun getWeather() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            val api = retrofit.create(MainApi::class.java)
            val modelData =
                api.getWeatherData(
                    API_KEY,
                    nameOfCity,
                    "no",
                    "ru"
                )
            model.liveDataCurrent.value = modelData
        }
    }

    companion object {

        private const val PERMISSION_REQUEST_ACCESS_LOCATION = 100

        @JvmStatic
        fun newInstance() = MainFragment()
    }

    private fun getCurrentLocation(): String {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(activity as Activity) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(activity, "Null Received", Toast.LENGTH_SHORT).show()
                    } else {
                        nameOfCity = ("${location.latitude},${location.longitude}")
                    }
                }
            } else {
                Toast.makeText(activity, "Turn on Location", Toast.LENGTH_SHORT).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            }
        } else {
            requestPermission()
        }
        return nameOfCity
    }

    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            activity as AppCompatActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    activity as AppCompatActivity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            activity as AppCompatActivity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }
}