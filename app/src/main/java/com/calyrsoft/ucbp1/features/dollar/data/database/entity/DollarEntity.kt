package com.calyrsoft.ucbp1.features.dollar.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dollars")
data class DollarEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val dollarOfficial: String? = null,
    val dollarParallel: String? = null,

    val oficialCompra: Double = 0.0,
    val oficialVenta: Double = 0.0,
    val paraleloCompra: Double = 0.0,
    val paraleloVenta: Double = 0.0,
    val fechaActualizacion: String = "",

    val timestamp: Long = 0L
)