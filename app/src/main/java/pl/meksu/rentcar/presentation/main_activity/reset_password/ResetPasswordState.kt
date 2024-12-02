package pl.meksu.rentcar.presentation.main_activity.reset_password

data class ResetPasswordState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)
