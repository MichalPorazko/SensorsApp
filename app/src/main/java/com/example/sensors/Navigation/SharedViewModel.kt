package com.example.sensors.Navigation

// SharedViewModel.kt
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.sensors.Firebase.FirebaseRepository

class SharedViewModel(
    private val firebaseRepository: FirebaseRepository
) : ViewModel() {


    fun signUpUser(
        email: String,
        password: String,
        userType: String,
        additionalInfo: Map<String, String>
    ) {
        firebaseRepository.signup(email, password, userType, additionalInfo)
    }

    fun loginUser(email: String, password: String) {
        firebaseRepository.login(email, password)
    }

    fun signOut() {
        firebaseRepository.signOut()
    }
}

