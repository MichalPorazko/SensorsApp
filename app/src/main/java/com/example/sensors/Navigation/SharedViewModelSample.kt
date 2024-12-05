package com.example.sensors.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.Screens.SignUpPage

@Composable
fun SharedViewModelSample() {
    val navController = rememberNavController()
    val firebaseRepository = remember { FirebaseRepository() }
    val sharedViewModel: SharedViewModel = viewModel(factory = SharedViewModelFactory(firebaseRepository))

    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        // Home Page
        composable("home") {
            HomePage(
                onLoginClick = { navController.navigate("login") },
                onSignUpClick = { navController.navigate("signup") }
            )
        }

        // Login Page
        composable("login") {
            LoginPage(
                sharedViewModel = sharedViewModel,
                onLoginSuccess = {
                    // Determine user type and navigate accordingly
                    determineUserTypeAndNavigate(navController, sharedViewModel)
                },
                onSignUpClick = { navController.navigate("signup") }
            )
        }

        // Sign Up Page
        composable("signup") {
            SignUpPage(
                sharedViewModel = sharedViewModel,
                onSignUpSuccess = {
                    // Determine user type and navigate accordingly
                    determineUserTypeAndNavigate(navController, sharedViewModel)
                },
                onLoginClick = { navController.navigate("login") }
            )
        }

        // Patient Main Screen
        composable("patient_main") {
            PatientMainScreen(sharedViewModel = sharedViewModel)
        }

        // Doctor Main Screen
        composable("doctor_main") {
            DoctorMainScreen(sharedViewModel = sharedViewModel)
        }
    }
}


private fun determineUserTypeAndNavigate(navController: NavController, viewModel: SharedViewModel) {
    // Fetch user info from Firestore
    val uid = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val db = FirebaseFirestore.getInstance()

    db.collection("users").document(uid).get()
        .addOnSuccessListener { document ->
            if (document != null && document.exists()) {
                val userType = document.getString("userType")
                if (userType == "Patient") {
                    navController.navigate("patient_main") {
                        popUpTo("home") { inclusive = true }
                    }
                } else if (userType == "Doctor") {
                    navController.navigate("doctor_main") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            } else {
                // Handle case where user document doesn't exist
            }
        }
        .addOnFailureListener { exception ->
            // Handle error
        }
}


@Composable
    inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(
        navController: NavController
    ): T {
        /**
         * Issue: If this.destination.parent?.route is null, viewModel()
         * is called without a scoped NavBackStackEntry.
         * This means that each composable will get its own instance of SharedViewModel
         * */

        /**
         * get the main graph, the parent graph
         *
         *  return viewModel()): -> parent route -> get back to get the ViewModel in the current scope
         * */
        val navGraphRoute: String = destination.parent?.route ?: return viewModel()

        // get the NavBackStackEntry assocciated with the parent graphh
        val parentEntry = remember(this){
            navController.getBackStackEntry(navGraphRoute)
        }
        return viewModel(parentEntry)
    }



