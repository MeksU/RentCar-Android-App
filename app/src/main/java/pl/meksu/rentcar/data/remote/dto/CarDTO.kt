package pl.meksu.rentcar.data.remote.dto

import pl.meksu.rentcar.domain.model.Car

data class CarDTO(
    val id: Int,
    val image: String,
    val model: String,
    val brand: String,
    val carDetails: CarDetailsDTO
)

fun CarDTO.toCar(): Car {
    return Car(
        id = id,
        image = image,
        model = model,
        brand = brand,
        power = carDetails.power,
        seats = carDetails.seats,
        type = carDetails.type,
        engine = carDetails.engine,
        fuelType = carDetails.fuelType,
        transmission = carDetails.transmission,
    )
}