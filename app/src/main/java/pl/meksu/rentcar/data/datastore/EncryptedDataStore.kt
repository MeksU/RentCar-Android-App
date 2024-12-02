package pl.meksu.rentcar.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import javax.crypto.SecretKey
import javax.inject.Inject

class EncryptedDataStore @Inject constructor(
    private val context: Context
) {
    private val secretKey: SecretKey = SimpleCrypto.createKeyFromPassword("secret_key_1207")

    private val Context.dataStore by preferencesDataStore(name = "encrypted_user_preferences")

    private val _jwtTokenKey = stringPreferencesKey("jwt_token")
    private val _userIdKey = stringPreferencesKey("user_id")
    private val _userNameKey = stringPreferencesKey("user_name")

    suspend fun saveUserData(userData: StoredUserData) {
        val encryptedToken = SimpleCrypto.encrypt(userData.jwtToken ?: "", secretKey)
        val encryptedUserId = SimpleCrypto.encrypt(userData.userId.toString(), secretKey)
        val encryptedUserName = SimpleCrypto.encrypt(userData.userName ?: "", secretKey)

        context.dataStore.edit { preferences ->
            preferences[_jwtTokenKey] = encryptedToken
            preferences[_userIdKey] = encryptedUserId
            preferences[_userNameKey] = encryptedUserName
        }
    }

    suspend fun loadUserData(): StoredUserData {
        val preferences = context.dataStore.data.first()
        val encryptedToken = preferences[_jwtTokenKey]
        val encryptedUserId = preferences[_userIdKey]
        val encryptedUserName = preferences[_userNameKey]

        val jwtToken = encryptedToken?.let { SimpleCrypto.decrypt(it, secretKey) }
        val userId = encryptedUserId?.let { SimpleCrypto.decrypt(it, secretKey).toIntOrNull() }
        val userName = encryptedUserName?.let { SimpleCrypto.decrypt(it, secretKey) }

        return StoredUserData(jwtToken, userId, userName)
    }

    suspend fun clearUserData() {
        context.dataStore.edit { it.clear() }
    }
}