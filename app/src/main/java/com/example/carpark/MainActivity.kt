package com.example.carpark

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.carpark.ui.theme.CarParkTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkLocationPermission()
        setupViewModel()
        checkShortcutIntent(intent)
        setContent {
            CarParkTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainActivityComposable(
                        { viewModel.saveActualLocation(this) },
                        { viewModel.navigateToLastSavedLocation(this) }
                    )
                }
            }
        }
    }

    private fun checkShortcutIntent(intent: Intent?) {
        if (intent?.data.toString() == "ADD_LOCATION") {
            setupViewModel()
            viewModel.saveActualLocation(this) {
                finishAffinity()
            }
        }
    }

    private fun setupViewModel() {
        viewModel = MainViewModel()
        viewModel.setUpSharedPreferences(this)
        viewModel.setUpLocationProvider(this)
    }

    private fun checkLocationPermission() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) {}
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }
}