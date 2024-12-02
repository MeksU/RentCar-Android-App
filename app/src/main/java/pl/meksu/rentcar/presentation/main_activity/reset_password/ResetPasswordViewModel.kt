package pl.meksu.rentcar.presentation.main_activity.reset_password

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.use_case.ResetPasswordUseCase
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private val _state = mutableStateOf(ResetPasswordState())
    val state: State<ResetPasswordState> = _state
    private val _email = mutableStateOf("")
    val email: State<String> = _email
    private val _resetCode = mutableStateOf("")
    val resetCode: State<String> = _resetCode
    private val _password = mutableStateOf("")
    val password: State<String> = _password
    private val _password2 = mutableStateOf("")
    val password2: State<String> = _password2
    private val _fragment = mutableIntStateOf(1)
    val fragment: State<Int> = _fragment

    fun requestReset() {
        viewModelScope.launch {
            if (_email.value.isEmpty()) {
                _state.value = ResetPasswordState(error = "Wprowadź adres e-mail!")
                return@launch
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_email.value).matches()) {
                _state.value = ResetPasswordState(error = "Wprowadź poprawny adres e-mail!")
                return@launch
            }

            resetPasswordUseCase.requestReset(_email.value).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = ResetPasswordState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = ResetPasswordState(isLoading = false, message = "Kod wysłany na podanego e-maila")
                        _fragment.intValue = 2
                    }
                    is Resource.Error -> {
                        _state.value = ResetPasswordState(isLoading = false, error = "Nie ma użytkownika o podanym adresie e-mail!")
                    }
                }
            }
        }
    }

    fun verifyResetCode() {
        viewModelScope.launch {
            if (resetCode.value.isEmpty()) {
                _state.value = ResetPasswordState(error = "Uzupełnij kod resetujący!")
                return@launch
            }

            resetPasswordUseCase.verifyResetCode(_email.value, _resetCode.value).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = ResetPasswordState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = ResetPasswordState(isLoading = false, message = "Kod weryfikacyjny poprawny, możesz zresetować hasło.")
                        _fragment.intValue = 3
                    }
                    is Resource.Error -> {
                        _state.value = ResetPasswordState(isLoading = false, error = "Nieprawidłowy kod weryfikacyjny.")
                    }
                }
            }
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            if (_email.value.isEmpty() || _password.value.isEmpty() || _password2.value.isEmpty()) {
                _state.value = ResetPasswordState(error = "Wypełnij wszystkie pola!")
                return@launch
            }

            if (_password.value != _password2.value) {
                _state.value = ResetPasswordState(error = "Hasła muszą być takie same!")
                return@launch
            }

            if (_password.value.length < 8) {
                _state.value = ResetPasswordState(error = "Hasło musi mieć co najmniej 8 znaków!")
                return@launch
            }

            resetPasswordUseCase.resetPassword(_email.value, _password.value).collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = ResetPasswordState(isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = ResetPasswordState(isLoading = false, message = "Hasło zostało pomyślnie zresetowane.")
                        _fragment.intValue = 4
                    }
                    is Resource.Error -> {
                        _state.value = ResetPasswordState(isLoading = false, error = "Wystąpił błąd podczas resetowania hasła.")
                    }
                }
            }
        }
    }

    fun clearState() {
        viewModelScope.launch {
            _state.value = ResetPasswordState()
        }
    }

    fun updateEmail(email: String) {
        _email.value = email
    }

    fun updateResetCode(resetCode: String) {
        _resetCode.value = resetCode
    }

    fun updatePassword(password: String) {
        _password.value = password
    }

    fun updateSecondPassword(password2: String) {
        _password2.value = password2
    }

    fun goBack() {
        _email.value = ""
        _resetCode.value = ""
        _password.value = ""
        _password2.value = ""
        _fragment.intValue = 1
    }
}