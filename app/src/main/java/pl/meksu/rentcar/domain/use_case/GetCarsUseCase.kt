package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.remote.dto.toCar
import pl.meksu.rentcar.domain.model.Car
import pl.meksu.rentcar.domain.repository.CarRepository
import javax.inject.Inject


class GetCarsUseCase @Inject constructor(
    private val repository: CarRepository
) {
    operator fun invoke(): Flow<Resource<List<Car>>> = flow {
        try {
            emit(Resource.Loading())
            val cars = repository.getAllCars().map { it.toCar() }
            emit(Resource.Success(cars))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response()?.errorBody()?.string() ?: "Nieoczekiwany błąd serwera!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}