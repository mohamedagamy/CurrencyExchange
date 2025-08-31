package com.example.paymob.data.repository

import com.example.paymob.data.api.ApiService
import com.example.paymob.data.model.CurrencyResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCurrency(fromCurrency: String): CurrencyResponse = apiService.getCurrency(fromCurrency)
    suspend fun getHistoricalRates(startDate:String) =  apiService.getHistoricalRates(startDate)
}