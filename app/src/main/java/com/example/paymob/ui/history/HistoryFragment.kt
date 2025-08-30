package com.example.paymob.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.paymob.vm.CurrencyViewModel
import com.example.paymob.vm.Error
import com.example.paymob.vm.Loading
import com.example.paymob.vm.Success
import com.example.paymob.databinding.FragmentHistoryBinding
import com.example.paymob.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.getValue

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CurrencyViewModel by viewModels()

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
        //viewModel.getCurrencyData()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is Loading -> {
                        context?.showToast("Loading....")
                    }
                    is Success -> {
                        context?.showToast("Completed")
                    }
                    is Error -> {
                        context?.showToast("Something wen wrong...")
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