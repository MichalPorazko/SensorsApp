package com.example.sensors.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.Screens.LoginPage
import com.example.sensors.Screens.PatientMainScreen
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
        composable("Settings"){}

        navigation(
            startDestination = "FirstPart",
            route = "auth"
        ){
            composable("login") {entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                LoginPage(viewModel, function = { navController.navigate("home") })

            }
            composable("signup") {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
                SignUpPage(
                    sharedViewModel = sharedViewModel,
                    onSignUpSuccess = {
                        // Determine user type and navigate accordingly
                        determineUserTypeAndNavigate(navController, sharedViewModel)
                    },
                    onLoginClick = { navController.navigate("login") }
                )
            }
            composable("forgot_password") {
                val viewModel = it.sharedViewModel<SharedViewModel>(navController)
            }

        }

        navigation(
            startDestination = "mainScreens",
            route = ""
        ){
            composable("patientScreen") {entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                PatientMainScreen (viewModel, function = navController.navigate())

            }
            composable("doctorScreen") {entry ->
                val viewModel = entry.sharedViewModel<SharedViewModel>(navController)
                DoctorMainScreen(sharedViewModel = sharedViewModel)

            }

        }

    }
}

fun NavGraphBuilder.appGraph(navController: NavController){
    navigation(startDestination = Screens.ScreenHomeRoute.route, route = Screens.AppRoute.route){
        composable(route = Screens.ScreenHomeRoute.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screens.ScreenARoute.route) {
            ScreenA(navController = navController)
        }
        composable(route = Screens.ScreenBRoute.route) {
            ScreenB(navController = navController)
        }
    }
}

fun NavGraphBuilder.authGraph(navController: NavController){

    navigation(startDestination = Screens.ScreenLoginRoute.route, route = Screens.AuthRoute.route){
        composable(route = Screens.ScreenLoginRoute.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screens.ScreenRegisterRoute.route) {
            SignUpPage { }(navController = navController)
        }
        composable(route = Screens.ScreenForgetPassRoute.route) {
            ForgetPassScreen(navController = navController)
        }
    }
}

@Composable
fun Nav() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screens.AuthRoute.route) {


        authGraph(navController)
        appGraph(navController)



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



