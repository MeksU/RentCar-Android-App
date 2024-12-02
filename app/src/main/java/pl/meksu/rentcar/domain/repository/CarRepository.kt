package pl.meksu.rentcar.domain.repository

import pl.meksu.rentcar.data.remote.dto.CarDTO

interface CarRepository {
    suspend fun getAllCars(): List<CarDTO>
}