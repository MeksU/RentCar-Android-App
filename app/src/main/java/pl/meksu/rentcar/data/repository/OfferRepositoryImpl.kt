package pl.meksu.rentcar.data.repository

import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.data.remote.dto.OfferDTO
import pl.meksu.rentcar.domain.repository.OfferRepository
import javax.inject.Inject

class OfferRepositoryImpl @Inject constructor(
    private val api: RentCarApi
): OfferRepository {
    override suspend fun getAllOffers(token: String): List<OfferDTO> {
        return api.getOffers(token)
    }
}