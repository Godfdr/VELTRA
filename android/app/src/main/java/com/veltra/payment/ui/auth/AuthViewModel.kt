package com.veltra.payment.ui.auth

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veltra.payment.data.VeltraRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AuthState(
    val username: String = "",
    val accountNumber: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val profilePhotoUri: String? = null,
    val isFaceIdEnabled: Boolean = true,
    val pin: String = "",
    val isAuthenticated: Boolean = false,
    val usernameError: String? = null,
    val isProcessing: Boolean = false
)

class AuthViewModel(private val repository: VeltraRepository) : ViewModel() {
    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            val savedUsername = repository.username.first()
            val savedAccount = repository.accountNumber.first()
            val savedPhoto = repository.profilePhoto.first()
            _state.update { it.copy(username = savedUsername, accountNumber = savedAccount, profilePhotoUri = savedPhoto) }
        }
    }

    fun validateUsername(username: String) {
        val trimmed = username.trim()
        if (trimmed.length < 5) {
            _state.update { it.copy(username = trimmed, usernameError = "Minimum 5 characters required") }
        } else if (!repository.isUsernameAvailable(trimmed)) {
            // Optimization: if current username is same as saved, it's "available" to the user
            viewModelScope.launch {
                val current = repository.username.first()
                if (trimmed.lowercase() == current.lowercase()) {
                    _state.update { it.copy(username = trimmed, usernameError = null) }
                } else {
                    _state.update { it.copy(username = trimmed, usernameError = "Username is already taken") }
                }
            }
        } else {
            _state.update { it.copy(username = trimmed, usernameError = null) }
        }
    }

    fun finalizeIdentity() {
        val currentUsername = _state.value.username
        if (_state.value.usernameError != null || currentUsername.length < 5) return

        _state.update { it.copy(isProcessing = true) }
        viewModelScope.launch {
            val accountNum = repository.finalizeUserIdentity(currentUsername)
            _state.update { it.copy(accountNumber = accountNum, isProcessing = false) }
        }
    }

    fun updateProfilePhoto(uri: Uri) {
        viewModelScope.launch {
            repository.saveProfilePhoto(uri)
            _state.update { it.copy(profilePhotoUri = uri.toString()) }
        }
    }

    fun saveUpdatedUsername() {
        val currentUsername = _state.value.username
        if (_state.value.usernameError != null || currentUsername.length < 5) return
        
        viewModelScope.launch {
            repository.updateUsername(currentUsername)
        }
    }

    fun updateRegistrationData(email: String, phone: String) {
        _state.update { it.copy(email = email, phoneNumber = phone) }
    }

    fun setAuthenticated(status: Boolean) {
        _state.update { it.copy(isAuthenticated = status) }
    }
}
