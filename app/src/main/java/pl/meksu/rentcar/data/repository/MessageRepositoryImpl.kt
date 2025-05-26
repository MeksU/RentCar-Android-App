package pl.meksu.rentcar.data.repository

import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.domain.model.UserMessage
import pl.meksu.rentcar.domain.repository.MessageRepository
import retrofit2.Response
import javax.inject.Inject

class MessageRepositoryImpl @Inject constructor(
    private val api: RentCarApi
): MessageRepository {
    override suspend fun sendMessage(token: String, userMessage: UserMessage): Response<Unit> {
        return api.sendMessage(token, userMessage)
    }
}