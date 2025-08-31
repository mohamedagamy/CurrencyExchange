package com.example.paymob.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.paymob.Constants
import com.example.paymob.data.cache.CacheManager
import com.example.paymob.data.model.CurrencyResponse
import com.example.paymob.domain.HistoricalRateUseCase
import com.example.paymob.data.common.Resource
import com.example.paymob.data.state.CurrencyUiState
import com.example.paymob.data.state.Error
import com.example.paymob.data.state.Loading
import com.example.paymob.data.state.Success
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HistoricalViewModel @Inject constructor(
    private val historicalRateUseCase: HistoricalRateUseCase,
    private val cacheManager: CacheManager
) : ViewModel() {

    val state = MutableStateFlow<CurrencyUiState>(Loading)
    fun getHistoricalRate(startDate:String) = viewModelScope.launch {
        if(cacheManager.hasCache(Constants.HISTORY_CACHE)) {
            val result = cacheManager.getFromCache(Constants.HISTORY_CACHE, TypeToken.get(
                CurrencyResponse::class.java))
            handleHistoricalRates(Resource.success(result))
        }else{
            withContext(Dispatchers.IO) {
                historicalRateUseCase(startDate).collect(::handleHistoricalRates)
            }
        }
    }


    private suspend fun handleHistoricalRates(it: Resource<CurrencyResponse?>) = withContext(Dispatchers.Main) {
        when (it.status) {
            Resource.Status.LOADING -> state.value = Loading
            Resource.Status.SUCCESS -> {
                state.value = Success(apiResult = it)
                cacheManager.saveToCache(Constants.HISTORY_CACHE, it.data)
            }
            Resource.Status.ERROR -> state.value =
                Error(error = it.error?.data?.message)
        }
    }
}