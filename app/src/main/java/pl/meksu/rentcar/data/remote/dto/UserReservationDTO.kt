package pl.meksu.rentcar.data.remote.dto

import pl.meksu.rentcar.domain.model.UserReservation
import java.time.LocalDate

data class UserReservationDTO(
    val id: Int,
    val offer: OfferDTO,
    val startDate: String,
    val endDate: String,
    val price: Double,
    val paymentStatus: String
)

fun UserReservationDTO.toUserReservation(): UserReservation {
    val userReservation = UserReservation(
        id = id,
        offer = offer.toOffer(),
        startDate = LocalDate.parse(startDate),
        endDate = LocalDate.parse(endDate),
        price = price,
        paymentStatus = paymentStatus
    )
    return userReservation
}
