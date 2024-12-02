package pl.meksu.rentcar.domain.model

import pl.meksu.rentcar.data.remote.dto.ReservationDTO
import java.time.LocalDate

data class Reservation(
    val user: Int,
    val offer: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)

fun Reservation.toDTO(): ReservationDTO {
    val reservationDTO = ReservationDTO(
        user = user,
        offer = offer,
        startDate = startDate.toString(),
        endDate = endDate.toString()
    )
    return reservationDTO
}