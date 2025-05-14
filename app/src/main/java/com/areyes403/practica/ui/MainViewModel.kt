package com.areyes403.practica.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.areyes403.practica.data.model.Location
import com.areyes403.practica.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel (
    private val repository: Repository
):ViewModel() {

    private val _location=MutableLiveData<Result<Location>>()
    val location:LiveData<Result<Location>> get() = _location

    private val _orders=MutableLiveData<List<Location>>()
    val orders:LiveData<List<Location>> get() = _orders

    private val _deliverStatus=MutableLiveData<DeliverState>()
    val deliverStatus:LiveData<DeliverState> get() = _deliverStatus

    init {
        getOrders()
    }

    fun getLocation() = viewModelScope.launch(Dispatchers.IO){
        try {
            val result=repository.getLocation()
            _location.postValue(Result.success(result))
        }catch (e: SecurityException) {
            _location.postValue(Result.failure(Exception("Permissions denied.")))
        } catch (e: IllegalStateException) {
            _location.postValue(Result.failure(Exception("Location client not loaded.")))
        } catch (e: Exception) {
            _location.postValue(Result.failure(Exception("Failed to get location: ${e.localizedMessage}")))
        }
    }

    private fun getOrders()=viewModelScope.launch(Dispatchers.IO){
        val result=repository.getOrders()
        _orders.postValue(result)
    }
    

}