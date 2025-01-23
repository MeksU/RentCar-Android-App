package pl.meksu.rentcar.presentation.main_activity.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.RegisterRequest
import pl.meksu.rentcar.domain.use_case.RegisterUseCase
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun register(
        name: String,
        surname: String,
        email: String,
        password: String,
        password2: String,
        phone: String,
        address: String,
        postalCode: String
    ) {
        viewModelScope.launch {
            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() ||
                password.isEmpty() || password2.isEmpty() || phone.isEmpty() ||
                address.isEmpty() || postalCode.isEmpty()) {
                _state.value = RegisterState(isLoading = false, error = "Wypełnij wszystkie pola formularza!")
                return@launch
            }

            if (password != password2) {
                _state.value = RegisterState(isLoading = false, error = "Hasła muszą być takie same!")
                return@launch
            }

            if (password.length < 8) {
                _state.value = RegisterState(isLoading = false, error = "Hasło musi mieć co najmniej 8 znaków!")
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                _state.value = RegisterState(isLoading = false, error = "Wprowadź poprawny adres e-mail!")
                return@launch
            }

            val registerRequest = RegisterRequest(
                name = name,
                surname = surname,
                mail = email,
                password = password,
                phone = phone,
                address = address,
                postalCode = postalCode
            )

            registerUseCase(registerRequest).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = RegisterState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = RegisterState(isLoading = false, message = result.data ?: "Rejestracja przebiegła pomyślnie")
                    }
                    is Resource.Error -> {
                        _state.value = RegisterState(isLoading = false, error = result.message ?: "Nieoczekiwany błąd.")
                    }
                }
            }
        }
    }

    fun clearState() {
        viewModelScope.launch {
            _state.value = RegisterState()
        }
    }
}