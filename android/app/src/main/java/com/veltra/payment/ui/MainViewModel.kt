package com.veltra.payment.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veltra.payment.data.VeltraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed class StartDestination {
    object Loading : StartDestination()
    data class Ready(val route: String) : StartDestination()
}

class MainViewModel(private val repository: VeltraRepository) : ViewModel() {
    private val _startDestination = MutableStateFlow<StartDestination>(StartDestination.Loading)
    val startDestination: StateFlow<StartDestination> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            val onboarded = repository.isOnboardingComplete.first()
            val loggedIn = repository.isLoggedIn.first()
            val route = if (!onboarded) "onboarding" else if (!loggedIn) "login" else "dashboard"
            _startDestination.value = StartDestination.Ready(route)
        }
    }

    fun setOnboardingComplete() {
        viewModelScope.launch { repository.setOnboardingComplete(true) }
    }

    fun setLoggedIn(status: Boolean) {
        viewModelScope.launch { repository.setLoggedIn(status) }
    }
}
