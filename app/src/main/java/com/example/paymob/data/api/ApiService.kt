package com.example.paymob.data.api

import com.example.paymob.data.model.CurrencyResponse
import retrofit2.http.*

//http://data.fixer.io/api/latest?access_key=0cc6c9fd211ecc4787b293ff65badb08
//http://data.fixer.io/api/2013-03-16?access_key=0cc6c9fd211ecc4787b293ff65badb08&symbols=GBP,EUR,USD,CAD,CHF,AUD,JPY,NZD,CNY,SGD
interface ApiService {
    @GET("latest?access_key=17f65e517b71fb08f03f04972f1c041f ")
    suspend fun getCurrency(@Query("base") baseCurrency: String): CurrencyResponse

    @GET("latest?access_key=17f65e517b71fb08f03f04972f1c041f")
    suspend fun getPopularCurrency(@Query("symbols") symbols:String = "GBP,EUR,USD,CAD,CHF,AUD,JPY,NZD,CNY,SGD"): CurrencyResponse

    @GET("{start_date}?access_key=17f65e517b71fb08f03f04972f1c041f")
    suspend fun getHistoricalRates(@Path("start_date") startDate:String ,@Query("symbols") symbols:String = "GBP,EUR,USD,CAD,CHF,AUD,JPY,NZD,CNY,SGD"): CurrencyResponse
}
