package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.api.ApiService
import com.example.currencyconverter.data.model.CurrencyResponse
import dagger.hilt.android.scopes.ActivityRetainedScoped

import javax.inject.Inject

@ActivityRetainedScoped
class CurrencyRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getCurrency(): CurrencyResponse = apiService.getCurrency()
    suspend fun getPopularCurrency() =  apiService.getPopularCurrency()
    suspend fun getHistoricalRates(startDate:String) =  apiService.getHistoricalRates(startDate)
}