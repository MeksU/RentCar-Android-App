package pl.meksu.rentcar.presentation.logged_user_activity.contact

data class ContactState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String = ""
)