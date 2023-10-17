package com.example.carpark

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainViewModel : ViewModel() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var sharedPref: SharedPreferences

    fun saveActualLocation(activity: Activity, doneCallback: (() -> Unit)? = null) {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        with(sharedPref.edit()) {
                            putString(
                                activity.getString(R.string.preference_file_key),
                                "${location.latitude}+${location.longitude}"
                            )
                            apply()
                            Toast.makeText(
                                activity,
                                activity.getString(R.string.saved),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            activity.getString(R.string.location_error),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    doneCallback?.invoke()
                }
        } else {
            Toast.makeText(
                activity,
                activity.getString(R.string.need_permission),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun navigateToLastSavedLocation(activity: Activity) {
        val location = sharedPref.getString(activity.getString(R.string.preference_file_key), "")
        if (!location.isNullOrEmpty()) {
            val locationArray = location.split("+")
            val gmmIntentUri: Uri = Uri.parse("google.navigation:q=${locationArray.first()},${locationArray[1]}&mode=w")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            activity.startActivity(mapIntent)
            removeSaveLocation(activity)
        } else {
            Toast.makeText(activity, activity.getString(R.string.no_saved), Toast.LENGTH_LONG)
                .show()
        }
    }

    private fun removeSaveLocation(activity: Activity) {
        sharedPref.edit().remove(activity.getString(R.string.preference_file_key)).apply()
    }

    fun setUpLocationProvider(activity: Activity) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
    }

    fun setUpSharedPreferences(activity: Activity) {
        sharedPref = activity.getSharedPreferences(
            activity.getString(R.string.preference_file_key), Context.MODE_PRIVATE
        )
    }
}