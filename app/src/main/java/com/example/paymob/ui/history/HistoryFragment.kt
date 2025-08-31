package com.example.paymob.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.paymob.data.cache.CacheManager
import com.example.paymob.data.model.CurrencyResponse
import com.example.paymob.data.state.Loading
import com.example.paymob.data.state.Success
import com.example.paymob.data.state.Error
import com.example.paymob.vm.CurrencyViewModel
import com.example.paymob.databinding.FragmentHistoryBinding
import com.example.paymob.ui.adapter.CurrencyAdapter
import com.example.paymob.utils.get4DaysAgo
import com.example.paymob.utils.get4DaysAgoText
import com.example.paymob.utils.getToday
import com.example.paymob.utils.showToast
import com.example.paymob.vm.HistoricalViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.getValue

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoricalViewModel by viewModels()

    @Inject
    lateinit var cacheManager: CacheManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onStart() {
        super.onStart()
        viewModel.getHistoricalRate(get4DaysAgo())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is Loading -> {
                        context?.showToast("Loading....")
                    }
                    is Success -> {
                        context?.showToast("Completed")
                        showData(state.apiResult.data as CurrencyResponse)
                    }
                    is Error -> {
                        context?.showToast("Something wen wrong...")
                    }
                }
            }
        }

    }

    fun showData(apiResult: CurrencyResponse?) {
        binding.tvDaysAgo.setText(apiResult?.date.toString())
        binding.tvBaseCurrency.setText(apiResult?.base.toString())
        val adapter = CurrencyAdapter()
        binding.rvHistory.adapter = adapter
        adapter.submitList(apiResult?.rates?.map { it.key + ":" + it.value })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}