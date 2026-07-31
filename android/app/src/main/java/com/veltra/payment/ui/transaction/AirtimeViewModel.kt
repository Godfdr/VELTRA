package com.veltra.payment.ui.transaction

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

data class AirtimeState(
    val phoneNumber: String = "",
    val selectedProvider: String = "MTN",
    val selectedAmount: BigDecimal = BigDecimal("1000")
)

class AirtimeViewModel : ViewModel() {
    private val _state = MutableStateFlow(AirtimeState())
    val state: StateFlow<AirtimeState> = _state.asStateFlow()

    fun updatePhoneNumber(number: String) {
        val cleaned = number.filter { it.isDigit() }
        _state.update { it.copy(phoneNumber = cleaned) }
    }

    fun updateProvider(provider: String) {
        _state.update { it.copy(selectedProvider = provider) }
    }

    fun updateAmount(amount: BigDecimal) {
        _state.update { it.copy(selectedAmount = amount) }
    }
}
