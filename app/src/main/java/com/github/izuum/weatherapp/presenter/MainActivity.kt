package com.github.izuum.weatherapp.presenter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.github.izuum.weatherapp.R
import com.github.izuum.weatherapp.presenter.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction()
            .replace(R.id.placeHolder, MainFragment.newInstance())
            .commit()
    }
}