package com.example.paymob.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.paymob.R
import com.example.paymob.databinding.FragmentHomeBinding
import com.example.paymob.vm.CurrencyViewModel
import com.example.paymob.vm.Error
import com.example.paymob.vm.Loading
import com.example.paymob.vm.Success
import com.example.paymob.data.cache.CacheManager
import com.example.paymob.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels()

    @Inject
    lateinit var cacheManager: CacheManager
    private var isSpinnerChange = false
    private var isTextChange = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    private fun setupSpinners() {
        val adapter = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_spinner_item,
            resources.getStringArray(R.array.currency_array))
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinnerFrom.adapter = adapter
        binding.spinnerTo.adapter = adapter

        binding.spinnerFrom.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        binding.spinnerTo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {}
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSpinners()
        binding.buttonConvert.setOnClickListener { convertCurrency() }
    }

    private fun convertCurrency() {
        val amount = binding.editTextFrom.text.toString().toDoubleOrNull()
        if (amount == null) {
            binding.textViewTo.setText("Please enter a valid amount")
            return
        }
        val fromCurrency = binding.spinnerFrom.selectedItem.toString()
        val toCurrency = binding.spinnerTo.selectedItem.toString()

        viewModel.getCurrencyData(fromCurrency)
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is Loading -> context?.showToast("Loading data...")
                    is Success -> state.apiResult?.let {
                        val rates = it?.rates
                        val conversionRate = rates?.get(toCurrency) ?: 0.0
                        val convertedAmount = amount * conversionRate
                        binding.textViewTo.setText(String.format("%2f",convertedAmount))

                    }
                    is Error -> state.error?.let { context?.showToast(it) }
                    else -> {
                        context?.showToast("something went wrong...")
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}