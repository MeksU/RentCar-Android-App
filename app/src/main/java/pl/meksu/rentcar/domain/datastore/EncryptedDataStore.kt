package pl.meksu.rentcar.domain.datastore

import pl.meksu.rentcar.domain.model.StoredUserData

interface EncryptedDataStore {
    suspend fun saveUserData(userData: StoredUserData)

    suspend fun loadUserData(): StoredUserData

    suspend fun clearUserData()
}