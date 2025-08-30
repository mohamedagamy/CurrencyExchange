package com.example.currencyconverter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconverter.data.cache.CacheManager
import com.example.currencyconverter.data.model.CurrencyResponse
import com.example.currencyconverter.domain.usecase.GetCurrencyUseCase
import com.example.currencyconverter.domain.usecase.HistoricalRateUseCase
import com.example.currencyconverter.domain.usecase.PopularCurrencyUseCase
import com.example.data.common.Resource
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(
    private val currencyUseCase: GetCurrencyUseCase,
    private val popularCurrencyUseCase: PopularCurrencyUseCase,
    private val historicalRateUseCase: HistoricalRateUseCase,
    private val cacheManager: CacheManager
) : ViewModel() {

    val state = MutableStateFlow<CurrencyUiState>(Loading)

    fun getCurrencyData() = viewModelScope.launch {
        if(cacheManager.hasCache(Constants.LATEST_CACHE)) {
            val result = cacheManager.getFromCache(Constants.LATEST_CACHE, TypeToken.get(CurrencyResponse::class.java))
            handleResponse(Resource.success(result) as Resource<CurrencyResponse>)
        } else {
            withContext(Dispatchers.IO) {
                currencyUseCase().collect(::handleResponse)
            }
        }
    }

    fun getPopularCurrency() = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            popularCurrencyUseCase().collect(::handleResponse)
        }
    }

    fun getHistoricalRate(startDate:String) = viewModelScope.async {
        withContext(Dispatchers.IO) {
            historicalRateUseCase(startDate).collect(::handleResponse)
        }
    }


    private suspend fun handleResponse(it: Resource<CurrencyResponse>) = withContext(Dispatchers.Main) {
        when (it.status) {
            Resource.Status.LOADING -> state.value = Loading
            Resource.Status.SUCCESS -> {
                state.value = Success(apiResult = it.data)
                cacheManager.saveToCache(Constants.LATEST_CACHE, it.data)
            }
            Resource.Status.ERROR -> state.value =
                Error(error = it.error?.data?.message)
        }
    }
}

sealed class CurrencyUiState
data class Success(val apiResult: CurrencyResponse?) : CurrencyUiState()
object Loading : CurrencyUiState()
class Error(val error: String? = null) : CurrencyUiState()