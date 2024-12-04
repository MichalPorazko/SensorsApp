package com.example.sensors.Navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sensors.Firebase.FirebaseRepository

class SharedViewModelFactory
    (private val firebaseRepository: FirebaseRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SharedViewModel(firebaseRepository) as T
        } else{
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}