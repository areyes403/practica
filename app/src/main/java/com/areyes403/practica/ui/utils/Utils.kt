package com.areyes403.practica.ui.utils

import com.areyes403.practica.data.utils.PERMISSION_ACCESS_COARSE_LOCATION
import com.areyes403.practica.data.utils.PERMISSION_ACCESS_FINE_LOCATION

object Utils {
    val requiredPermissions= arrayOf(
        PERMISSION_ACCESS_FINE_LOCATION,
        PERMISSION_ACCESS_COARSE_LOCATION
    )

}