package pl.meksu.rentcar.presentation.logged_user_activity.offer_detail

import pl.meksu.rentcar.domain.model.Reservation

data class OfferDetailState(
    val isLoading: Boolean = false,
    val reservations: List<Reservation> = emptyList(),
    val error: String = ""
)

data class CreateReservationState(
    val isLoading: Boolean = false,
    val success: String = "",
    val error: String = ""
)
