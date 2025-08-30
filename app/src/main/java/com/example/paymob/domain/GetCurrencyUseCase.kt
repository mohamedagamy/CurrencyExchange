package com.example.paymob.domain

import com.example.paymob.data.common.Resource
import com.example.paymob.data.model.CurrencyResponse
import com.example.paymob.data.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {

    private var currencyResponse = CurrencyResponse()

    suspend operator fun invoke(fromCurrency: String): Flow<Resource<CurrencyResponse>> =
        flow<Resource<CurrencyResponse>> {
            try {
                emit(Resource.loading())
                currencyResponse = repository.getCurrency(fromCurrency)
                emit(Resource.success(currencyResponse))
            } catch (e: Throwable) {
                emit(Resource.error(e))
            }
        }.flowOn(defaultDispatcher)

}

