package pl.meksu.rentcar.data.repository

import pl.meksu.rentcar.common.BackendResponse
import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.data.remote.model.LoginRequest
import pl.meksu.rentcar.data.remote.model.LoginResponse
import pl.meksu.rentcar.data.remote.model.RegisterRequest
import pl.meksu.rentcar.domain.repository.UserRepository
import retrofit2.Response
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: RentCarApi
): UserRepository {
    override suspend fun login(mail: String, password: String): LoginResponse {
        return api.login(LoginRequest(mail, password))
    }

    override suspend fun register(registerRequest: RegisterRequest): Response<BackendResponse> {
        return api.register(registerRequest)
    }

    override suspend fun refreshToken(token: String): LoginResponse {
        return api.refreshToken(token)
    }

    override suspend fun requestPasswordReset(email: String): Response<Unit> {
        return api.requestReset(email)
    }

    override suspend fun verifyResetCode(email: String, resetCode: String): Response<Unit> {
        return api.verifyResetCode(email, resetCode)
    }

    override suspend fun resetPassword(email: String, newPassword: String): Response<Unit> {
        return api.resetPassword(email, newPassword)
    }
}