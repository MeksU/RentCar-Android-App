package pl.meksu.rentcar.data.repository

import pl.meksu.rentcar.data.remote.RentCarApi
import pl.meksu.rentcar.data.remote.dto.ReservationDTO
import pl.meksu.rentcar.data.remote.dto.UserReservationDTO
import pl.meksu.rentcar.data.remote.model.Payment
import pl.meksu.rentcar.domain.repository.ReservationRepository
import retrofit2.Response
import javax.inject.Inject

class ReservationRepositoryImpl @Inject constructor(
    private val api: RentCarApi
): ReservationRepository {
    override suspend fun getOffersReservations(token: String, offerId: Int): List<ReservationDTO> {
        return api.getOffersReservations(token, offerId)
    }

    override suspend fun createReservation(token: String, reservationDTO: ReservationDTO): Response<Unit> {
        return api.createReservation(token, reservationDTO)
    }

    override suspend fun getUsersReservations(token: String, userId: Int): List<UserReservationDTO> {
        return api.getUsersReservations(token, userId)
    }

    override suspend fun deleteReservation(token: String, reservationId: Int): Response<Unit> {
        return api.deleteReservation(token, reservationId)
    }

    override suspend fun addPayment(token: String, payment: Payment): Response<Unit> {
        return api.addPayment(token, payment)
    }
}