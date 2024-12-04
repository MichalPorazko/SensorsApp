package com.example.sensors.Navigation

import android.hardware.Sensor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.sensors.Firebase.FirebaseRepository
import com.example.sensors.Screens.MainScreen
import com.example.sensors.Screens.SensorMeasurementScreen

@Composable
fun SharedViewModelSample() {
    val navController = rememberNavController()
    val firebaseRepository = remember { FirebaseRepository() }
    val sharedViewModel: SharedViewModel = viewModel(factory = SharedViewModelFactory(firebaseRepository))
    NavHost(
        navController = navController,
        startDestination = "main_graph"
    ) {
        navigation(
            startDestination = "main_screen",
            route = "main_graph"
        ) {
            composable("main_screen") { entry: NavBackStackEntry ->
                // collectAsStateWithLifecycle() => collects data
                val sensorResults by sharedViewModel.sensorResults.collectAsStateWithLifecycle()

                MainScreen(
                    sensorResults = sensorResults,
                    sensorFunction = { sensorType ->
                        navController.navigate("sensor_screen/$sensorType")
                    })
            }

            composable(
                route = "sensor_screen/{sensorType}",
                /**
                 * navArgument is used to define the arguments expected by a navigation route in the navigation graph
                 * */
                arguments = listOf(navArgument("sensorType") { type = NavType.IntType })
            ) { entry: NavBackStackEntry ->
                val sensorType = entry.arguments?.getInt("sensorType") ?: Sensor.TYPE_ACCELEROMETER

                SensorMeasurementScreen(
                    sensorType = sensorType,
                    onMeasurementFinished = {
                        navController.popBackStack()
                    },
                    sharedViewModel
                )
            }

        }
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



