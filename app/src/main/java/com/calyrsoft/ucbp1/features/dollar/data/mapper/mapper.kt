package com.calyrsoft.ucbp1.features.dollar.data.mapper

import com.calyrsoft.ucbp1.features.dollar.data.database.entity.DollarEntity
import com.calyrsoft.ucbp1.features.dollar.domain.model.DollarModel

fun DollarEntity.toModel(): DollarModel {
    return DollarModel(
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        oficialCompra = oficialCompra,
        oficialVenta = oficialVenta,
        paraleloCompra = paraleloCompra,
        paraleloVenta = paraleloVenta,
        fechaActualizacion = fechaActualizacion,
        timestamp = timestamp
    )
}

fun DollarModel.toEntity(): DollarEntity {
    return DollarEntity(
        dollarOfficial = dollarOfficial,
        dollarParallel = dollarParallel,
        oficialCompra = oficialCompra,
        oficialVenta = oficialVenta,
        paraleloCompra = paraleloCompra,
        paraleloVenta = paraleloVenta,
        fechaActualizacion = fechaActualizacion,
        timestamp = if (timestamp == 0L) System.currentTimeMillis() else timestamp
    )
}

data class DollarFirebaseModel(
    val oficialCompra: Double = 0.0,
    val oficialVenta: Double = 0.0,
    val paraleloCompra: Double = 0.0,
    val paraleloVenta: Double = 0.0,
    val fechaActualizacion: String = "",
    val timestamp: Long = 0L
)

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