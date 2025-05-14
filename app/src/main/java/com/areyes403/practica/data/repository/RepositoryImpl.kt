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
        return Location(latitude = lastLocation.latitude, longitude = lastLocation.longitude)
    }

    override suspend fun getOrders(): List<Location> = listOf(
        Location(latitude = 20.636988, longitude = -103.4111739),
        Location(latitude = 20.6464443, longitude = -103.3450322)
    )
}