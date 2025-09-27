// features/dollar/data/datasource/RealTimeRemoteDataSource.kt
package com.calyrsoft.ucbp1.features.dollar.data.datasource

import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

// AGREGA ESTA CLASE DENTRO DEL ARCHIVO
data class DollarFirebaseModel(
    val oficialCompra: Double = 0.0,
    val oficialVenta: Double = 0.0,
    val paraleloCompra: Double = 0.0,
    val paraleloVenta: Double = 0.0,
    val fechaActualizacion: String = "",
    val timestamp: Long = 0L
)

// AGREGA ESTA FUNCIÓN DE EXTENSIÓN
fun DollarFirebaseModel.toDomain(): DollarModel {
    return DollarModel(
        dollarOfficial = oficialCompra.toString(),
        dollarParallel = paraleloCompra.toString(),
        oficialCompra = oficialCompra,
        oficialVenta = oficialVenta,
        paraleloCompra = paraleloCompra,
        paraleloVenta = paraleloVenta,
        fechaActualizacion = fechaActualizacion,
        timestamp = if (timestamp == 0L) System.currentTimeMillis() else timestamp
    )
}

class RealTimeRemoteDataSource {

    fun getDollarUpdates(): Flow<DollarModel> = callbackFlow {
        val callback = object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                close(error.toException())
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue(DollarFirebaseModel::class.java)
                if (value != null) {
                    val dollarModel = value.toDomain()
                    trySend(dollarModel)
                } else {
                    // Datos por defecto si no hay conexión
                    trySend(DollarModel(
                        dollarOfficial = "0",
                        dollarParallel = "0",
                        oficialCompra = 0.0,
                        oficialVenta = 0.0,
                        paraleloCompra = 0.0,
                        paraleloVenta = 0.0,
                        fechaActualizacion = "No disponible",
                        timestamp = System.currentTimeMillis()
                    ))
                }
            }
        }

        val database = Firebase.database
        val myRef = database.getReference("dollar_rates/latest")
        myRef.addValueEventListener(callback)

        awaitClose {
            myRef.removeEventListener(callback)
        }
    }
}