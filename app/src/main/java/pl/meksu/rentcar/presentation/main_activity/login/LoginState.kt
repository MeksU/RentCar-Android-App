package pl.meksu.rentcar.presentation.main_activity.login

import pl.meksu.rentcar.domain.model.LoginResponse

data class LoginState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val loginResponse: LoginResponse? = null,
    val error: String = ""
)