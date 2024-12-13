package com.example.sensors.Screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.Navigation.SharedViewModel
import com.example.sensors.Navigation.SharedViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val firebaseRepository = remember { FirebaseRepository() }
            val sharedViewModel: SharedViewModel =
                viewModel<SharedViewModel>(factory = SharedViewModelFactory(firebaseRepository))
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("Home"){
                    ProfileChooseScreen(
                        navigate2Login = {navController.navigate("Login")}
                    )
                }

                composable("Login"){
                    LoginPage(
                        sharedViewModel = sharedViewModel,
                        function = { navController.navigate("home") }
                    )
                }

//                composable("Settings") {}
//
//                navigation(
//                    startDestination = "FirstPart",
//                    route = "auth"
//                ) {
//                    composable("login") { entry ->
//                        val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
//                        LoginPage(viewModel, function = { navController.navigate("home") })
//
//                    }
//                    composable("signup") {
//                        val viewModel = it.sharedViewModel<SharedViewModel>(navController)
//                        SignUpPage(
//                            sharedViewModel = sharedViewModel,
//                            onSignUpSuccess = {
//                                // Determine user type and navigate accordingly
//                                determineUserTypeAndNavigate(navController, sharedViewModel)
//                            },
//                            onLoginClick = { navController.navigate("login") }
//                        )
//                    }
//                    composable("forgot_password") {
//                        val viewModel = it.sharedViewModel<SharedViewModel>(navController)
//                    }
//
//                }
//
//                navigation(
//                    startDestination = "mainScreens",
//                    route = ""
//                ) {
//                    composable("patientScreen") { entry ->
//                        val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
//                        PatientMainScreen(viewModel, function = navController.navigate())
//
//                    }
//                    composable("doctorScreen") { entry ->
//                        val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
//                        DoctorMainScreen(sharedViewModel = sharedViewModel)
//
//                    }
//
                }


        }
    }
}


