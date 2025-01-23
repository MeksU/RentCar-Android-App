package pl.meksu.rentcar.domain.repository

import pl.meksu.rentcar.data.remote.dto.ReservationDTO
import pl.meksu.rentcar.data.remote.dto.UserReservationDTO
import pl.meksu.rentcar.domain.model.Payment
import retrofit2.Response

interface ReservationRepository {
    suspend fun getOffersReservations(token: String, offerId: Int): List<ReservationDTO>
    suspend fun createReservation(token: String, reservationDTO: ReservationDTO): Response<Unit>
    suspend fun getUsersReservations(token: String, userId: Int): List<UserReservationDTO>
    suspend fun deleteReservation(token: String, reservationId: Int): Response<Unit>
    suspend fun addPayment(token: String, payment: Payment): Response<Unit>
}