package com.calyrsoft.ucbp1.features.dollar.domain.model

data class DollarModel(
    var dollarOfficial: String? = null,
    var dollarParallel: String? = null,

    val oficialCompra: Double = 0.0,
    val oficialVenta: Double = 0.0,
    val paraleloCompra: Double = 0.0,
    val paraleloVenta: Double = 0.0,
    val fechaActualizacion: String = "",

    val timestamp: Long = 0L
)