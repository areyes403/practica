package com.areyes403.practica.ui

import com.areyes403.practica.data.model.Location

data class DeliverState(
    val toDeliver:Location? = null,
    val nextToDeliver:Location? = null,
    val orders:List<Location> = listOf(),
    val finished:Boolean = false
)
