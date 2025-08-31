package com.example.paymob.data.state

import com.example.paymob.data.common.Resource

sealed class CurrencyUiState
data class Success(val apiResult: Resource<*>) : CurrencyUiState()
object Loading : CurrencyUiState()
class Error(val error: String? = null) : CurrencyUiState()