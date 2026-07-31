package com.veltra.payment.ui.pockets

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal

data class AddFundsState(
    val amount: String = "25000",
    val autoSaveEnabled: Boolean = false
) {
    val numericAmount: BigDecimal 
        get() = amount.replace(",", "").toBigDecimalOrNull() ?: BigDecimal.ZERO
}

class AddFundsViewModel : ViewModel() {
    private val _state = MutableStateFlow(AddFundsState())
    val state: StateFlow<AddFundsState> = _state.asStateFlow()

    fun updateAmount(amount: String) {
        val cleaned = amount.filter { it.isDigit() || it == '.' }
        _state.update { it.copy(amount = cleaned) }
    }

    fun toggleAutoSave(enabled: Boolean) {
        _state.update { it.copy(autoSaveEnabled = enabled) }
    }
}
