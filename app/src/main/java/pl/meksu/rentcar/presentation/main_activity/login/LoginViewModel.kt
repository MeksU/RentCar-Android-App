package pl.meksu.rentcar.presentation.main_activity.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.model.StoredUserData
import pl.meksu.rentcar.domain.use_case.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val encryptedDataStore: EncryptedDataStore
) : ViewModel() {

    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state
    private val _email = mutableStateOf("")
    val email: State<String> = _email
    private val _password = mutableStateOf("")
    val password: State<String> = _password

    fun login() {
        viewModelScope.launch {
            if(_email.value.isEmpty() || _password.value.isEmpty()) {
                _state.value = LoginState(error = "Wypełnij wszystkie pola formularza!")
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
                _state.value = LoginState(isLoading = false, error = "Wprowadź poprawny adres e-mail!")
                return@launch
            }

            loginUseCase(_email.value, _password.value).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = LoginState(isLoggedIn = true, loginResponse = result.data)

                        result.data?.let { loginResponse ->
                            val newData = StoredUserData(loginResponse.jwt, loginResponse.userId, loginResponse.userName)
                            encryptedDataStore.saveUserData(newData)
                        }
                    }
                    is Resource.Error -> {
                        _state.value = LoginState(error = result.message ?: "Login failed")
                    }
                    is Resource.Loading -> {
                        _state.value = LoginState(isLoading = true)
                    }
                }
            }
        }
    }

    fun updateEmail(newMail: String) {
        _email.value = newMail
    }

    fun updatePassword(newPassword: String) {
        _password.value = newPassword
    }

    fun clearState() {
        viewModelScope.launch {
            _state.value = LoginState()
        }
    }
}
