package pl.meksu.rentcar.presentation.main_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.model.StoredUserData
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
            val savedToken = withContext(Dispatchers.IO) {
                encryptedDataStore.loadUserData()
            }

            refreshTokenUseCase(savedToken.jwtToken ?: "d").collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _loggedIn.value = true

                        result.data?.let { loginResponse ->
                            val newData = StoredUserData(loginResponse.jwt, loginResponse.userId, loginResponse.userName)
                            withContext(Dispatchers.IO) {
                                encryptedDataStore.saveUserData(newData)
                            }
                        }
                    }

                    is Resource.Error -> {
                        _loggedIn.value = false
                        withContext(Dispatchers.IO) {
                            encryptedDataStore.clearUserData()
                        }
                    }

                    is Resource.Loading -> {
                        _loggedIn.value = false
                    }
                }
            }
        }
    }
}
