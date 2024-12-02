package pl.meksu.rentcar.data.remote.dto

import pl.meksu.rentcar.domain.model.Offer

data class OfferDTO(
    val id: Int,
    val car: CarDTO,
    val price: Double,
    val description: String,
    val promotion: Double
)

fun OfferDTO.toOffer(): Offer {
    return Offer(
        id = id,
        car = car.toCar(),
        price = price,
        description = description,
        promotion = promotion
    )
}