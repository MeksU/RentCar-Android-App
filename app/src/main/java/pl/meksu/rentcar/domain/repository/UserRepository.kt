package pl.meksu.rentcar.domain.repository

import pl.meksu.rentcar.common.BackendResponse
import pl.meksu.rentcar.domain.model.LoginResponse
import pl.meksu.rentcar.domain.model.RegisterRequest
import retrofit2.Response

interface UserRepository {
    suspend fun login(mail: String, password: String): LoginResponse
    suspend fun register(registerRequest: RegisterRequest): Response<BackendResponse>
    suspend fun refreshToken(token: String): LoginResponse
    suspend fun requestPasswordReset(email: String): Response<Unit>
    suspend fun verifyResetCode(email: String, resetCode: String): Response<Unit>
    suspend fun resetPassword(email: String, newPassword: String): Response<Unit>
}