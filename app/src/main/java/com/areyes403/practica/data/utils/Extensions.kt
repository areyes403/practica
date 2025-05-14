package com.areyes403.practica.data.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun Activity.checkPermissionGranted(permission:String):Boolean{
    return ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED
}

fun Activity.snackBar(msg:String?){
    this.window.decorView.rootView.let { rootView ->
        Snackbar.make(rootView, "Error: $msg", Snackbar.LENGTH_SHORT).show()
    }
}