package pl.meksu.rentcar.domain.use_case

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.model.RegisterRequest
import pl.meksu.rentcar.domain.repository.UserRepository
import pl.meksu.rentcar.common.ErrorParser
import retrofit2.HttpException
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    operator fun invoke(registerRequest: RegisterRequest): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.register(registerRequest)
            if(response.isSuccessful) {
                val successJson = response.body()
                emit(Resource.Success(successJson?.message ?: "Użytkowniki zarejestrowany pomyślnie!"))
            } else {
                val errorJson = response.errorBody()?.string()
                val errorMessage = ErrorParser.parseErrorResponse(errorJson)
                emit(Resource.Error(message = errorMessage ?: "Wystąpił błąd podczas rejestracji"))
            }
        } catch (e: HttpException) {
            val errorJson = e.response()?.errorBody()?.string()
            val errorMessage = ErrorParser.parseErrorResponse(errorJson)
            emit(Resource.Error(message = errorMessage))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Brak połączenia z internetem. Spróbuj ponownie."))
        }
    }
}