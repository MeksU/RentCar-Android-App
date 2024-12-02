package pl.meksu.rentcar.domain.use_case

import retrofit2.HttpException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.remote.dto.toOffer
import pl.meksu.rentcar.domain.model.Offer
import pl.meksu.rentcar.domain.repository.OfferRepository
import javax.inject.Inject

class GetOffersUseCase @Inject constructor(
    private val repository: OfferRepository
) {
    operator fun invoke(token: String): Flow<Resource<List<Offer>>> = flow {
        try {
            emit(Resource.Loading())
            val offers = repository.getAllOffers(token).map { it.toOffer() }
            emit(Resource.Success(offers))
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.response()?.errorBody()?.string() ?: "Nieoczekiwany błąd serwera!"))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Nie można połączyć się z serwerem. Sprawdź połączenie z internetem."))
        }
    }
}