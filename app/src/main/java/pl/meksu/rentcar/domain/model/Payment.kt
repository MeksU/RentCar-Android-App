package pl.meksu.rentcar.domain.model

data class Payment(
    val reservationId: Int,
    val paymentNumber: String
)