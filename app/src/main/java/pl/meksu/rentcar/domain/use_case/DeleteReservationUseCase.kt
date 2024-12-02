package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.repository.ReservationRepository
import javax.inject.Inject

class DeleteReservationUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    operator fun invoke(token: String, userId: Int): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.deleteReservation(token, userId)
            if (response.isSuccessful) {
                emit(Resource.Success("Rezerwacja przebiegła pomyślnie!"))
            } else {
                emit(Resource.Error(message = "Nie udało się usunąć rezerwacji."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }
}