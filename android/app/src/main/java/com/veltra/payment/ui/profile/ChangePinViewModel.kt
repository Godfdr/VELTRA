package com.veltra.payment.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veltra.payment.data.VeltraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ChangePinState(
    val currentPin: String = "",
    val newPin: String = "",
    val confirmPin: String = "",
    val step: PinStep = PinStep.ENTER_CURRENT,
    val error: String? = null
)

enum class PinStep { ENTER_CURRENT, ENTER_NEW, CONFIRM_NEW, SUCCESS }

class ChangePinViewModel(private val repository: VeltraRepository) : ViewModel() {
    private val _state = MutableStateFlow(ChangePinState())
    val state: StateFlow<ChangePinState> = _state.asStateFlow()

    fun onNumberClick(number: String) {
        _state.update { currentState ->
            val updatedPin = when (currentState.step) {
                PinStep.ENTER_CURRENT -> if (currentState.currentPin.length < 4) currentState.currentPin + number else currentState.currentPin
                PinStep.ENTER_NEW -> if (currentState.newPin.length < 4) currentState.newPin + number else currentState.newPin
                PinStep.CONFIRM_NEW -> if (currentState.confirmPin.length < 4) currentState.confirmPin + number else currentState.confirmPin
                else -> ""
            }
            
            val nextState = when (currentState.step) {
                PinStep.ENTER_CURRENT -> currentState.copy(currentPin = updatedPin)
                PinStep.ENTER_NEW -> currentState.copy(newPin = updatedPin)
                PinStep.CONFIRM_NEW -> currentState.copy(confirmPin = updatedPin)
                else -> currentState
            }
            
            if (updatedPin.length == 4) {
                // Fintech-grade validation against secure encrypted store
                val actualCurrentPin = repository.getSecurePin()
                
                when (currentState.step) {
                    PinStep.ENTER_CURRENT -> {
                        if (updatedPin == actualCurrentPin) {
                            nextState.copy(step = PinStep.ENTER_NEW, error = null)
                        } else {
                            nextState.copy(currentPin = "", error = "Incorrect current PIN")
                        }
                    }
                    PinStep.ENTER_NEW -> nextState.copy(step = PinStep.CONFIRM_NEW, error = null)
                    PinStep.CONFIRM_NEW -> {
                        if (updatedPin == currentState.newPin) {
                            repository.saveSecurePin(updatedPin)
                            nextState.copy(step = PinStep.SUCCESS, error = null)
                        } else {
                            nextState.copy(confirmPin = "", error = "PINs do not match")
                        }
                    }
                    else -> nextState
                }
            } else {
                nextState.copy(error = null)
            }
        }
    }

    fun onDeleteClick() {
        _state.update { currentState ->
            when (currentState.step) {
                PinStep.ENTER_CURRENT -> currentState.copy(currentPin = currentState.currentPin.dropLast(1))
                PinStep.ENTER_NEW -> currentState.copy(newPin = currentState.newPin.dropLast(1))
                PinStep.CONFIRM_NEW -> currentState.copy(confirmPin = currentState.confirmPin.dropLast(1))
                else -> currentState
            }
        }
    }
}
