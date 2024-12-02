package pl.meksu.rentcar.data.remote.dto

data class CarDetailsDTO(
    val id: Int,
    val power: Int,
    val seats: Int,
    val type: String,
    val engine: String,
    val fuelType: String,
    val transmission: String
)