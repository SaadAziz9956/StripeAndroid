package com.example.stripeintegration.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stripeintegration.data.model.Response
import com.example.stripeintegration.domain.repository.MainRepository
import com.example.stripeintegration.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel
@Inject constructor(
    private val repo: MainRepository
) : ViewModel() {

    private val _result = Channel<Response>()
    val result = _result.receiveAsFlow()

    fun init() {
        viewModelScope.launch {
            repo.createCustomer().collectLatest { result ->
                when (result) {
                    is DataState.Loading -> Unit
                    is DataState.Error -> {
                        Timber.d("Error: ${result.message}")
                    }
                    is DataState.Success -> {
                        Timber.d("Success: ${result.data?.customerId} ${result.data?.key} ${result.data?.clientSecret}")
                        result.data?.let {
                            _result.send(it)
                        }
                    }
                }
            }
        }
    }

}