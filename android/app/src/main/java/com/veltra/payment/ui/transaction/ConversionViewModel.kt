package com.veltra.payment.ui.transaction

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.math.BigDecimal
import java.math.RoundingMode

data class CurrencyData(val country: String, val code: String, val symbol: String, val flag: String)

data class ConversionState(
    val fromCurrency: CurrencyData = CurrencyData("United States", "USD", "$", "🇺🇸"),
    val toCurrency: CurrencyData = CurrencyData("Nigeria", "NGN", "₦", "🇳🇬"),
    val fromAmount: String = "50000",
    val conversionRate: BigDecimal = BigDecimal("1571.16"),
    val fee: BigDecimal = BigDecimal("0.10")
) {
    val numericFromAmount: BigDecimal
        get() = fromAmount.replace(",", "").toBigDecimalOrNull() ?: BigDecimal.ZERO
    
    val toAmount: BigDecimal 
        get() = numericFromAmount.multiply(conversionRate).setScale(2, RoundingMode.HALF_UP)
        
    val totalToAmount: BigDecimal 
        get() = if (numericFromAmount > BigDecimal.ZERO) toAmount.subtract(fee) else BigDecimal.ZERO
}

class ConversionViewModel : ViewModel() {
    private val _state = MutableStateFlow(ConversionState())
    val state: StateFlow<ConversionState> = _state.asStateFlow()

    fun updateFromCurrency(currency: CurrencyData) {
        _state.update { it.copy(fromCurrency = currency) }
    }

    fun updateToCurrency(currency: CurrencyData) {
        _state.update { it.copy(toCurrency = currency) }
    }

    fun swapCurrencies() {
        _state.update { currentState ->
            val newRate = BigDecimal.ONE.divide(currentState.conversionRate, 6, RoundingMode.HALF_UP)
            val currentToNumeric = currentState.toAmount
            currentState.copy(
                fromCurrency = currentState.toCurrency,
                toCurrency = currentState.fromCurrency,
                conversionRate = newRate,
                fromAmount = currentToNumeric.toPlainString()
            )
        }
    }

    fun updateAmount(amount: String) {
        val cleaned = amount.filterIndexed { index, char ->
            char.isDigit() || (char == '.' && amount.indexOf('.') == index)
        }
        _state.update { it.copy(fromAmount = cleaned) }
    }
}
