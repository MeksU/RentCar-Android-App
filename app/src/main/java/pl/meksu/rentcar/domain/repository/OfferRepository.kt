package pl.meksu.rentcar.domain.repository

import pl.meksu.rentcar.data.remote.dto.OfferDTO

interface OfferRepository {
    suspend fun getAllOffers(token: String): List<OfferDTO>
}