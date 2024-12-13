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
//
//    fun loginUser(email: String, password: String, onSuccess: () -> Unit, onError: (String) -> Unit) {
//        firebaseRepository.login(email, password)
//        authState.observeForever { authState ->
//            when (authState) {
//                is FirebaseRepository.AuthState.Authenticated -> onSuccess()
//                is FirebaseRepository.AuthState.Error -> onError(authState.message)
//                else -> {}
//            }
//        }
//    }
//
//    private fun login() {
//
//        loginInProgress.value = true
//        val email = loginUIState.value.email
//        val password = loginUIState.value.password
//
//        FirebaseAuth
//            .getInstance()
//            .signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener {
//                Log.d(TAG,"Inside_login_success")
//                Log.d(TAG,"${it.isSuccessful}")
//
//                if(it.isSuccessful){
//                    loginInProgress.value = false
//                    PostOfficeAppRouter.navigateTo(Screen.HomeScreen)
//                }
//            }
//            .addOnFailureListener {
//                Log.d(TAG,"Inside_login_failure")
//                Log.d(TAG,"${it.localizedMessage}")
//
//                loginInProgress.value = false
//
//            }
//
//    }

    fun signOut() {
        firebaseRepository.signOut()
    }


}

