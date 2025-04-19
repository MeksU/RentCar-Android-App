package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.remote.dto.toReservation
import pl.meksu.rentcar.domain.model.Reservation
import pl.meksu.rentcar.domain.model.toDTO
import pl.meksu.rentcar.domain.repository.ReservationRepository
import javax.inject.Inject

class OfferReservationsUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    operator fun invoke(token: String, offerId: Int): Flow<Resource<List<Reservation>>> = flow {
        try {
            emit(Resource.Loading())
            val reservations = repository.getOffersReservations(token, offerId).map { it.toReservation() }
            emit(Resource.Success(reservations))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response()?.errorBody()?.string() ?: "Nieoczekiwany błąd serwera!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)

    operator fun invoke(token: String, reservation: Reservation): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.createReservation(token, reservation.toDTO())
            if (response.isSuccessful) {
                emit(Resource.Success("Rezerwacja przebiegła pomyślnie!"))
            } else {
                emit(Resource.Error(message = "Nie udało się dokonać rezerwacji."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}