package pl.meksu.rentcar.data.repository

import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.data.remote.dto.CarDTO
import pl.meksu.rentcar.domain.repository.CarRepository
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val api: RentCarApi
): CarRepository {
    override suspend fun getAllCars(): List<CarDTO> {
        return api.getCars()
    }
}