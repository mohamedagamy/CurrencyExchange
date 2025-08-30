package com.example.currencyconverter.domain.usecase

import com.example.data.common.Resource
import com.example.currencyconverter.data.model.CurrencyResponse
import com.example.currencyconverter.data.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class PopularCurrencyUseCase @Inject constructor(
    private val repository: CurrencyRepository,
    private val defaultDispatcher: CoroutineDispatcher
) {

    private var currencyResponse = CurrencyResponse()

    suspend operator fun invoke(): Flow<Resource<CurrencyResponse>> =
        flow<Resource<CurrencyResponse>> {
            try {
                emit(Resource.loading())
                currencyResponse = repository.getPopularCurrency()
                emit(Resource.success(currencyResponse))
            } catch (e: Throwable) {
                emit(Resource.error(e))
            }
        }.flowOn(defaultDispatcher)

}

