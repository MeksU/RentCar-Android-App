package pl.meksu.rentcar.domain.repository

import pl.meksu.rentcar.domain.model.UserMessage
import retrofit2.Response

interface MessageRepository {
    suspend fun sendMessage(token: String, userMessage: UserMessage): Response<Unit>
}