package pl.meksu.rentcar.domain.use_case

import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.LoginResponse
import pl.meksu.rentcar.domain.repository.UserRepository
import pl.meksu.rentcar.common.ErrorParser
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(mail: String, password: String): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val loginResponse = userRepository.login(mail, password)
            emit(Resource.Success(loginResponse))
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()
            val errorMessage = ErrorParser.parseErrorResponse(errorJson)
            emit(Resource.Error(message = errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }
}