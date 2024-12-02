package pl.meksu.rentcar.domain.model

import java.time.LocalDate

data class UserReservation(
    val id: Int,
    val offer: Offer,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val price: Double,
    val paymentStatus: String
)