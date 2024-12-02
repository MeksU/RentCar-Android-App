package pl.meksu.rentcar.data.remote.dto

import pl.meksu.rentcar.domain.model.Reservation
import java.time.LocalDate

data class ReservationDTO(
    val user: Int,
    val offer: Int,
    val startDate: String,
    val endDate: String
)

fun ReservationDTO.toReservation(): Reservation {
    val reservation = Reservation(
        user = user,
        offer = offer,
        startDate = LocalDate.parse(startDate),
        endDate = LocalDate.parse(endDate)
    )
    return reservation
}
