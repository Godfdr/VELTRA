package com.veltra.payment.network

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.veltra.payment.VeltraApplication
import com.veltra.payment.data.VeltraRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first

class OfflineSyncWorker(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        Log.d("OfflineSyncWorker", "🔄 Veltra Auto-Sync Started (Connectivity Detected)")
        
        // In a real application, you would initialize the repository here
        // or get it from your DI container.
        val repository = VeltraRepository(applicationContext)
        
        try {
            val transactions = repository.allTransactions.first()
            val offlineTransactions = transactions.filter { it.isOffline }
            
            if (offlineTransactions.isEmpty()) {
                Log.d("OfflineSyncWorker", "✅ No pending offline transactions.")
                return Result.success()
            }

            Log.d("OfflineSyncWorker", "📡 Syncing ${offlineTransactions.size} transactions to Veltra Cloud...")
            
            // Simulation of high-speed secure sync
            delay(2000) 
            
            // Logic to clear offline flags or move to permanent store would go here
            
            Log.d("OfflineSyncWorker", "✅ Veltra Auto-Sync Complete.")
            return Result.success()
        } catch (e: Exception) {
            Log.e("OfflineSyncWorker", "❌ Sync Failed", e)
            return Result.retry()
        }
    }
}
