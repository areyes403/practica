package com.areyes403.practica.ui

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.areyes403.practica.R
import com.areyes403.practica.data.repository.RepositoryImpl
import com.areyes403.practica.data.utils.PERMISSION_ACCESS_COARSE_LOCATION
import com.areyes403.practica.data.utils.PERMISSION_ACCESS_FINE_LOCATION
import com.areyes403.practica.data.utils.checkPermissionGranted
import com.areyes403.practica.data.utils.snackBar
import com.areyes403.practica.databinding.ActivityMainBinding
import com.areyes403.practica.ui.utils.Utils
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : AppCompatActivity() {

    private val fusedLocationProviderClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(applicationContext)
    }

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    private val locationPermissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        when {
            permissions.getOrDefault(PERMISSION_ACCESS_FINE_LOCATION, false) -> {
                viewModel.getLocation()
            }

            permissions.getOrDefault(PERMISSION_ACCESS_COARSE_LOCATION, false) -> {
                viewModel.getLocation()
            }
            else -> snackBar("Location permissions is required for this feature.")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Manual injecting
        val repository=RepositoryImpl(fusedLocationProviderClient)
        viewModel=MainViewModel(repository = repository)

        //Setting view
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        checkPermissions {
            viewModel.getLocation()
        }
        observers()
        onClick()
    }

    private fun observers(){
        viewModel.location.observe(this){ result->
            result.onSuccess {
                binding.txtMyLocation.text="(Current Location) Latitude:${it.latitude}, Longitude:${it.longitude}"
            }.onFailure {
                binding.txtMyLocation.text=it.message
            }
        }
        viewModel.orders.observe(this){ list->
            if (list.size<3){
                val first=list[0]
                binding.txtFirstOrder.text="${first.name} Latitude:${first.latitude}, Longitude:${first.longitude}"
                val second=list[1]
                binding.txtSecondOrder.text="${second.name} Latitude:${second.latitude}, Longitude:${second.longitude}"
            }
        }
        viewModel.deliverStatus.observe(this){ status->
            binding.txtToDelivery.text="To delivery: ${status.toDeliver?.name} \n Next To Delivery: ${status.nextToDeliver?.name}"
            when{
                status.orders.isEmpty()->{
                    binding.btnDeliver.text = if (status.toDeliver==null) "START" else "FINISH"
                }
//                status.orders.size==1->{
//                    binding.btnDeliver.text="FINISH"
//                }
                else->{
                    binding.btnDeliver.text="DELIVER"
                }
            }
        }
    }

    private fun onClick(){
        binding.btnDeliver.setOnClickListener {
            checkPermissions {
                viewModel.deliver()
            }
        }
    }

    private fun checkPermissions(onSuccess:()->Unit){
        when{
            checkPermissionGranted(PERMISSION_ACCESS_FINE_LOCATION)->{
                onSuccess()
            }
            checkPermissionGranted(PERMISSION_ACCESS_COARSE_LOCATION)->{
                onSuccess()
            }
            else -> {
                locationPermissionRequest.launch(Utils.requiredPermissions)
            }
        }
    }


}