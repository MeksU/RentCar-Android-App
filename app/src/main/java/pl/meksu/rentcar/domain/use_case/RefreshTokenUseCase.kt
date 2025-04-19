package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import pl.meksu.rentcar.common.ErrorParser
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.LoginResponse
import pl.meksu.rentcar.domain.repository.UserRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(token: String): Flow<Resource<LoginResponse>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.refreshToken(token)
            emit(Resource.Success(response))
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()
            val errorMessage = ErrorParser.parseErrorResponse(errorJson)
            emit(Resource.Error(message = errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Brak połączenia z internetem. Spróbuj ponownie."))
        }
    }.flowOn(Dispatchers.IO)
}