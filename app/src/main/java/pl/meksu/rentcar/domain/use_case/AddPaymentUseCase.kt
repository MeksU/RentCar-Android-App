package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.Payment
import pl.meksu.rentcar.domain.repository.ReservationRepository
import javax.inject.Inject

class AddPaymentUseCase @Inject constructor(
    private val repository: ReservationRepository
) {
    operator fun invoke(token: String, payment: Payment): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.addPayment(token, payment)
            if (response.isSuccessful) {
                emit(Resource.Success("Płatność przebiegła pomyślnie!"))
            } else {
                emit(Resource.Error(message = "Nie udało się przetworzyć płatności."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}