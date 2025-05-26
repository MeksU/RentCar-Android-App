package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.UserMessage
import pl.meksu.rentcar.domain.repository.MessageRepository
import java.io.IOException
import javax.inject.Inject

class SendMessageUseCase @Inject constructor(
    private val repository: MessageRepository
) {
    operator fun invoke(token: String, message: UserMessage): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = repository.sendMessage(token, message)
            if (response.isSuccessful) {
                emit(Resource.Success("Wysłano wiadomość!"))
            } else {
                emit(Resource.Error(message = "Nie udało się wysłać wiadomości."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}