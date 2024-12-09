package com.example.sensors.Navigation

sealed class Screens(val route: String){

    object ScreenLoginRoute : Screens(route = "Login")
    object ScreenForgetPassRoute : Screens(route = "ForgetPass")
    object ScreenRegisterRoute : Screens(route = "Register")
    object ScreenPatientMainRoute : Screens(route = "PatientMain")
    object ScreenDoctorMainRoute : Screens(route = "DoctorMain")
    object AuthRoute : Screens(route = "Auth")
    object AppRoute : Screens(route = "App")
}