package pl.meksu.rentcar.presentation.main_activity.register

data class RegisterState(
    val isLoading: Boolean = false,
    val message: String = "",
    val error: String = ""
)