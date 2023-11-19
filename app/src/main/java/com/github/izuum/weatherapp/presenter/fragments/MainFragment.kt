package com.github.izuum.weatherapp.presenter.fragments

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.github.izuum.weatherapp.data.liveData.cityName
import com.github.izuum.weatherapp.presenter.viewModel.MainViewModel
import com.github.izuum.weatherapp.databinding.FragmentMainBinding
import com.github.izuum.weatherapp.data.extensions.checkPermission
import com.github.izuum.weatherapp.data.extensions.isLocationEnabled
import com.github.izuum.weatherapp.data.extensions.requestPermission
import com.github.izuum.weatherapp.presenter.viewModel.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.text.Typography.degree

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var model: MainViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        allAction()
        swipeFun()
    }

    private fun init() {
        handler = Handler()
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        model = ViewModelProvider(
            this, MainViewModelFactory(this))
            .get(MainViewModel::class.java)
    }

    private fun swipeFun() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            runnable = Runnable {
                allAction()
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

    @SuppressLint("SetTextI18n")
    private fun updateWeatherInfo() = with(binding) {
        model.forecastLive.observe(viewLifecycleOwner) {
            tvCityName.text = it.location.name
            tvTemp.text = it.current.temp_c.toInt().toString() + degree
            tvDescription.text = it.current.condition.text
            tvLastUpdate.text = it.current.last_updated
            Glide.with(requireContext())
                .load("https:" + it.current.condition.icon)
                .into(binding.imageView)
        }
    }

    companion object {
        const val PERMISSION_REQUEST_ACCESS_LOCATION = 100
        @JvmStatic
        fun newInstance() = MainFragment()
    }
    private fun allAction(){
        CoroutineScope(Dispatchers.Main).launch {
            getCurrentLocation()
            delay(1000)
            model.get()
            updateWeatherInfo()
        }
    }

    private fun getCurrentLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(activity as Activity) { task ->
                    val location: Location? = task.result
                    if (location == null) {
                        Toast.makeText(activity, "Null Received", Toast.LENGTH_SHORT).show()
                    } else {
                        cityName.value = ("${location.latitude},${location.longitude}")
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
    }
}