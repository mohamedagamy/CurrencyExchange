package com.example.paymob.data.model

data class ExchangeHistoryResponse(
    val success: Boolean? = false,
    val timeseries: Boolean? = false,
    val start_date: String? = "",
    val end_date: String? ="",
    val base: String? = "",
    val rates: Map<String, Map<String, Double>>? = emptyMap()
)