package com.example.sensors.Firebase

import android.util.Log
import com.example.sensors.SensorData.SensorResult
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class FirebaseRepository {

    private val db: FirebaseFirestore =
        FirebaseFirestore.getInstance()


    private val auth: FirebaseAuth =
        FirebaseAuth.getInstance()


    fun saveSensorResult(sensorType: String, result: SensorResult) {
        Log.d("FirebaseRepository", "saveSensorResult called with sensorType: $sensorType, result: $result")
        try {
            db.collection(sensorType).add(result)
                .addOnSuccessListener {
                    Log.d("FirebaseRepository", "Data saved successfully.")
                }
                .addOnFailureListener { exception ->
                    Log.e("FirebaseRepository", "Error saving data: ${exception.message}", exception) // Log the exception as well
                }
        } catch (e: Exception) {
            Log.e("FirebaseRepository", "An unexpected error occurred: ${e.message}", e) // Catch and log any unexpected exceptions
        }
    }


}