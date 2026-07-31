package com.veltra.payment

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.veltra.payment.network.OfflineSyncWorker
import java.util.concurrent.TimeUnit

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "veltra_settings")

class VeltraApplication : Application() {
    
    override fun onCreate() {
        super.onCreate()
        setupAutoSync()
    }

    private fun setupAutoSync() {
        // Constraints: Must have internet connection
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Schedule periodic check for offline transactions every 1 hour
        // (WorkManager also triggers immediately when network becomes available if a job is pending)
        val syncRequest = PeriodicWorkRequestBuilder<OfflineSyncWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "VeltraOfflineSync",
            androidx.work.ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }
}
