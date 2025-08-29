package com.example.currencyconverter.api

import retrofit2.http.GET

interface ApiService {
    @GET("endpoint")
    suspend fun getData(): Any
}
