package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.remote.dto.toUserReservation
import pl.meksu.rentcar.domain.model.UserReservation
import pl.meksu.rentcar.domain.repository.ReservationRepository
import javax.inject.Inject

class GetUserReservationsUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    operator fun invoke(token: String, userId: Int): Flow<Resource<List<UserReservation>>> = flow {
        try {
            emit(Resource.Loading())
            val reservations = repository.getUsersReservations(token, userId).map { it.toUserReservation() }
            emit(Resource.Success(reservations))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response()?.errorBody()?.string() ?: "Nieoczekiwany błąd serwera!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}