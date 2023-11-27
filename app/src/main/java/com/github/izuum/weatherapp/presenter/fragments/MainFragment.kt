package com.github.izuum.weatherapp.presenter.fragments

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.github.izuum.weatherapp.data.getLocation.GetLocation
import com.github.izuum.weatherapp.presenter.viewModel.MainViewModel
import com.github.izuum.weatherapp.databinding.FragmentMainBinding
import com.github.izuum.weatherapp.presenter.viewModel.MainViewModelFactory
import kotlin.text.Typography.degree

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locate = GetLocation(requireContext())
        locate.getCurrentLocation()
        viewModel.getWeather()
        updateWeatherInfo()
        swipeFun()
    }

    private fun swipeFun() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            val locate = GetLocation(requireContext())
            locate.getCurrentLocation()
            viewModel.getWeather()
            updateWeatherInfo()
            binding.swipeRefreshLayout.isRefreshing = false
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
        viewModel.forecastLive.observe(viewLifecycleOwner) {
            tvCityName.text = it.name
            tvTemp.text = it.tempC.toInt().toString() + degree
            tvDescription.text = it.text
            tvLastUpdate.text = it.lastUpdated
            Glide.with(requireContext())
                .load("https:" + it.icon)
                .into(binding.imageView)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}