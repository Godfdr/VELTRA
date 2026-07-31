package com.veltra.payment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veltra.payment.data.VeltraRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DashboardViewModel(private val repository: VeltraRepository) : ViewModel() {

    val isBalanceVisible: StateFlow<Boolean> = repository.isBalanceVisible
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun toggleBalanceVisibility() {
        viewModelScope.launch {
            repository.saveBalanceVisibility(!isBalanceVisible.value)
        }
    }
}
