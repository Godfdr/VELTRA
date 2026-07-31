package com.veltra.payment.data

import android.content.Context
import android.net.Uri
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.veltra.payment.dataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import java.util.UUID
import kotlin.random.Random

/**
 * Transaction entity for Veltra Secure Store.
 */
data class VeltraTransaction(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val amount: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isIncome: Boolean = false,
    val isOffline: Boolean = false
)

/**
 * Hardened Repository for Veltra Smart Banking.
 */
class VeltraRepository(private val context: Context) {

    private val gson = Gson()
    
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val securePrefs = EncryptedSharedPreferences.create(
        context,
        "veltra_secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    companion object {
        private val USERNAME_KEY = stringPreferencesKey("user_tag")
        private val ACCOUNT_NUMBER_KEY = stringPreferencesKey("user_account_number")
        private val PROFILE_PHOTO_KEY = stringPreferencesKey("user_profile_photo")
        private val BALANCE_VISIBLE_KEY = booleanPreferencesKey("balance_visible")
        private val ONBOARDING_COMPLETE_KEY = booleanPreferencesKey("onboarding_complete")
        private val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
        
        private const val PIN_SECURE_KEY = "user_pin_secure"
        private const val TRANSACTIONS_SECURE_KEY = "user_transactions_secure"
        private const val ALL_USERNAMES_KEY = "all_usernames_registry"
        private const val ALL_ACCOUNTS_KEY = "all_accounts_registry"
    }

    // Secure Data (Encrypted PIN)
    fun getSecurePin(): String = securePrefs.getString(PIN_SECURE_KEY, "1234") ?: "1234"
    
    fun saveSecurePin(pin: String) {
        securePrefs.edit().putString(PIN_SECURE_KEY, pin).apply()
    }

    // --- Identity System (Prefix 7) ---

    fun isUsernameAvailable(username: String): Boolean {
        if (username.length < 5) return false
        val registry = getRegistry(ALL_USERNAMES_KEY)
        return !registry.contains(username.lowercase())
    }

    suspend fun finalizeUserIdentity(username: String): String {
        val lowerUsername = username.lowercase()
        val accountNumber = generateUniqueAccountNumber()
        
        context.dataStore.edit { 
            it[USERNAME_KEY] = lowerUsername
            it[ACCOUNT_NUMBER_KEY] = accountNumber
        }
        
        addToRegistry(ALL_USERNAMES_KEY, lowerUsername)
        addToRegistry(ALL_ACCOUNTS_KEY, accountNumber)
        
        return accountNumber
    }

    suspend fun updateUsername(newUsername: String) {
        val oldUsername = _transactions.value.let { "" } // Just for type inference context
        context.dataStore.edit { it[USERNAME_KEY] = newUsername.lowercase() }
        addToRegistry(ALL_USERNAMES_KEY, newUsername.lowercase())
    }

    suspend fun saveProfilePhoto(uri: Uri) {
        context.dataStore.edit { it[PROFILE_PHOTO_KEY] = uri.toString() }
    }

    private fun generateUniqueAccountNumber(): String {
        val registry = getRegistry(ALL_ACCOUNTS_KEY)
        var attempt: String
        do {
            // Updated Prefix to '7' as per user request
            attempt = "7" + (1..9).map { Random.nextInt(0, 10) }.joinToString("")
        } while (registry.contains(attempt))
        return attempt
    }

    private fun getRegistry(key: String): Set<String> {
        val json = securePrefs.getString(key, null) ?: return emptySet()
        val type = object : TypeToken<Set<String>>() {}.type
        return gson.fromJson(json, type)
    }

    private fun addToRegistry(key: String, value: String) {
        val current = getRegistry(key).toMutableSet()
        current.add(value)
        securePrefs.edit().putString(key, gson.toJson(current)).apply()
    }

    // SQL Data (Encrypted Transactions & Offline Mode)
    private val _transactions = MutableStateFlow<List<VeltraTransaction>>(loadTransactions())
    val allTransactions: Flow<List<VeltraTransaction>> = _transactions

    fun addTransaction(transaction: VeltraTransaction) {
        val current = loadTransactions().toMutableList()
        current.add(0, transaction)
        securePrefs.edit().putString(TRANSACTIONS_SECURE_KEY, gson.toJson(current)).apply()
        _transactions.value = current
    }

    private fun loadTransactions(): List<VeltraTransaction> {
        val json = securePrefs.getString(TRANSACTIONS_SECURE_KEY, null) ?: return emptyList()
        return try {
            val type = object : TypeToken<List<VeltraTransaction>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Standard Preferences (DataStore)
    val username: Flow<String> = context.dataStore.data.map { it[USERNAME_KEY] ?: "alexveltra" }
    val accountNumber: Flow<String> = context.dataStore.data.map { it[ACCOUNT_NUMBER_KEY] ?: "7000000000" }
    val profilePhoto: Flow<String?> = context.dataStore.data.map { it[PROFILE_PHOTO_KEY] }
    val isBalanceVisible: Flow<Boolean> = context.dataStore.data.map { it[BALANCE_VISIBLE_KEY] ?: true }
    val isOnboardingComplete: Flow<Boolean> = context.dataStore.data.map { it[ONBOARDING_COMPLETE_KEY] ?: false }
    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { it[IS_LOGGED_IN_KEY] ?: false }

    suspend fun saveBalanceVisibility(visible: Boolean) {
        context.dataStore.edit { it[BALANCE_VISIBLE_KEY] = visible }
    }

    suspend fun setOnboardingComplete(complete: Boolean) {
        context.dataStore.edit { it[ONBOARDING_COMPLETE_KEY] = complete }
    }

    suspend fun setLoggedIn(loggedIn: Boolean) {
        context.dataStore.edit { it[IS_LOGGED_IN_KEY] = loggedIn }
    }
}
