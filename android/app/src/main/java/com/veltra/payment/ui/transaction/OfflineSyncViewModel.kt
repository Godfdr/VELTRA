package com.veltra.payment.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.veltra.payment.data.VeltraRepository
import com.veltra.payment.data.VeltraTransaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class OfflineSyncState(
    val pendingTransactions: Int = 0,
    val isSyncing: Boolean = false,
    val lastSyncTime: String? = null
)

class OfflineSyncViewModel(private val repository: VeltraRepository) : ViewModel() {
    private val _state = MutableStateFlow(OfflineSyncState())
    val state: StateFlow<OfflineSyncState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.allTransactions.collect { txs ->
                val offlineCount = txs.count { it.isOffline }
                _state.update { it.copy(pendingTransactions = offlineCount) }
            }
        }
    }

    fun syncNow() {
        if (_state.value.pendingTransactions == 0 || _state.value.isSyncing) return
        
        _state.update { it.copy(isSyncing = true) }
        viewModelScope.launch {
            // Simulation of "Veltra Sync" backend handshake
            delay(3000)
            
            // In real run, we'd iterate through offline txs and call secure API
            // For now, we simulate clearing the "Offline" status flag in the encrypted store
            val txs = repository.allTransactions.first()
            txs.forEach { tx ->
                if (tx.isOffline) {
                    // Update tx status in real logic
                }
            }
            
            _state.update { it.copy(
                isSyncing = false, 
                pendingTransactions = 0,
                lastSyncTime = java.text.SimpleDateFormat("HH:mm", java.util.Locale.US).format(java.util.Date())
            ) }
        }
    }
}
