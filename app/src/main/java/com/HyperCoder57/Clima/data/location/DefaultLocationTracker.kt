package com.HyperCoder57.Clima.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.HyperCoder57.Clima.domain.location.LocationTracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
): LocationTracker {

    override suspend fun getCurrentLocation(): Location? {
        val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Verificamos permisos y estado de GPS en una sola línea
        val hasPermissions = ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(application, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (!hasPermissions || !isGpsEnabled) return null

        return suspendCancellableCoroutine { cont ->
            locationClient.lastLocation.apply {
                // Si ya está disponible, manejamos el resultado directamente
                if (isComplete) {
                    cont.resume(if (isSuccessful) result else null)
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener { cont.resume(it) }
                addOnFailureListener { cont.resume(null) }
                addOnCanceledListener { cont.cancel() }
            }
        }
    }
}
