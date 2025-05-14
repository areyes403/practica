package com.areyes403.practica.repository

import com.areyes403.practica.data.model.Location

interface Repository {
    suspend fun getLocation():Location
    suspend fun getOrders():List<Location>
}