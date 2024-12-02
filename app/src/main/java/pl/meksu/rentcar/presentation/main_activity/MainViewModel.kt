package pl.meksu.rentcar.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.datastore.EncryptedDataStore
import pl.meksu.rentcar.data.datastore.StoredUserData
import pl.meksu.rentcar.domain.use_case.RefreshTokenUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val refreshTokenUseCase: RefreshTokenUseCase,
    private val encryptedDataStore: EncryptedDataStore
) : ViewModel() {
    private val _loggedIn = mutableStateOf(false)
    val loggedIn: State<Boolean> = _loggedIn

    fun tryToLogin() {
        viewModelScope.launch {
            val savedToken = encryptedDataStore.loadUserData()

            refreshTokenUseCase(savedToken.jwtToken ?: "d").collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _loggedIn.value = true

                        result.data?.let { loginResponse ->
                            val newData = StoredUserData(loginResponse.jwt, loginResponse.userId, loginResponse.userName)
                            encryptedDataStore.saveUserData(newData)
                        }
                    }

                    is Resource.Error -> {
                        _loggedIn.value = false
                        encryptedDataStore.clearUserData()
                    }

                    is Resource.Loading -> {
                        _loggedIn.value = false
                    }
                }
            }
        }
    }
}