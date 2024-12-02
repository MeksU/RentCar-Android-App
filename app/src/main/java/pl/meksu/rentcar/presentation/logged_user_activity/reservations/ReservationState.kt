package pl.meksu.rentcar.presentation.logged_user_activity.reservations

import pl.meksu.rentcar.domain.model.UserReservation

data class ReservationState(
    val isLoading: Boolean = false,
    val reservations: List<UserReservation> = emptyList(),
    val error: String = ""
)

data class DeleteReservationState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = ""
)
