package com.example.sensors.Authentication

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.sensors.Firebase.FirebaseRepository
import javax.xml.validation.Validator

class LoginViewModel(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val loginUIState = mutableStateOf(LoginUIStatus())

    val validationRules = mutableStateOf(false)


    fun onEvent(event: LoginUIEvent) {
        validateLoginUIDataWithRules()
        when (event) {
            is LoginUIEvent.EmailChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    email = event.email
                )
            }

            is LoginUIEvent.PasswordChanged -> {
                loginUIState.value = loginUIState.value.copy(
                    password = event.password
                )
            }

            is LoginUIEvent.LoginButtonClicked -> {
            }
        }

    }

    private fun validateLoginUIDataWithRules() {
        val emailResult = ValidationRules.validateEmail(
            email = loginUIState.value.email
        )

        val passwordResult = ValidationRules.validatePassword(
            password = loginUIState.value.password
        )

        loginUIState.value = loginUIState.value.copy(
            emailError = emailResult.status,
            passwordError = passwordResult.status
        )

        validationRules.value = emailResult.status && passwordResult.status


    }

    private fun login() {
        firebaseRepository.loginUser(loginUIState.value.email, loginUIState.value.password)
        firebaseRepository.authState.observeForever {
            when (it) {
                is FirebaseRepository.AuthState.Authenticated -> {
                    // Navigate to home screen
                }
                is FirebaseRepository.AuthState.Error -> {
                    // Show error message
                }
                is FirebaseRepository.AuthState.Loading -> {
                    // Show loading
                }
            }
        }
    }


}