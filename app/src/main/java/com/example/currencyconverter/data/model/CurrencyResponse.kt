package com.example.currencyconverter.data.model

data class CurrencyResponse(
    val base: String? = "",
    val date: String? = "",
    val rates: HashMap<String,Double> = hashMapOf(),
    val success: Boolean? = false,
    val error: Error? =  null,
    val timestamp: Int? = 0)

data class Error(
    val code: Int,
    val info: String,
    val type: String
)

data class Currency(
    val currencyKey: String = "",
    val currencyValue: String = ""
)