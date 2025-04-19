package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.repository.UserRepository
import java.io.IOException
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    fun requestReset(email: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.requestPasswordReset(email)
            if (response.isSuccessful) {
                emit(Resource.Success("E-mail resetowy został wysłany."))
            } else {
                emit(Resource.Error(message = "Nie udało się wysłać e-maila resetowego."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Brak połączenia z internetem."))
        }
    }.flowOn(Dispatchers.IO)

    fun verifyResetCode(email: String, resetCode: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.verifyResetCode(email, resetCode)
            if (response.isSuccessful) {
                emit(Resource.Success("Kod poprawny"))
            } else {
                emit(Resource.Error(message = "Kod jest nieprawidłowy."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Brak połączenia z internetem."))
        }
    }.flowOn(Dispatchers.IO)

    fun resetPassword(email: String, newPassword: String): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.resetPassword(email, newPassword)
            if (response.isSuccessful) {
                emit(Resource.Success("Hasło zostało zresetowane."))
            } else {
                emit(Resource.Error(message = "Nie udało się zresetować hasła."))
            }
        } catch (e: IOException) {
            emit(Resource.Error(message = "Brak połączenia z internetem."))
        }
    }.flowOn(Dispatchers.IO)
}