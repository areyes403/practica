package com.areyes403.practica.data.repository

import android.annotation.SuppressLint
import com.areyes403.practica.data.model.Location
import com.areyes403.practica.repository.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.tasks.await

class RepositoryImpl (
    private val fusedLocationProviderClient: FusedLocationProviderClient
) :Repository {

    override suspend fun getLocation(): Location {
        val lastLocation = fusedLocationProviderClient.lastLocation.await()
        return Location(latitude = lastLocation.latitude, longitude = lastLocation.longitude, name = "My Location")
    }

    override suspend fun getOrders(): List<Location> = listOf(
        Location(latitude = 20.7403974, longitude = -103.3472027, name = "First Location"),
        Location(latitude = 20.7013204, longitude = -103.3513022, name = "Second Location")
    )
}