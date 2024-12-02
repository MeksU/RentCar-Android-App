package pl.meksu.rentcar.data.remote

import pl.meksu.rentcar.common.BackendResponse
import pl.meksu.rentcar.data.remote.dto.CarDTO
import pl.meksu.rentcar.data.remote.dto.OfferDTO
import pl.meksu.rentcar.data.remote.dto.ReservationDTO
import pl.meksu.rentcar.data.remote.dto.UserReservationDTO
import pl.meksu.rentcar.data.remote.model.LoginRequest
import pl.meksu.rentcar.data.remote.model.LoginResponse
import pl.meksu.rentcar.data.remote.model.Payment
import pl.meksu.rentcar.data.remote.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RentCarApi {
    @GET("api/cars")
    suspend fun getCars(): List<CarDTO>

    @POST("api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body registerRequest: RegisterRequest): Response<BackendResponse>

    @POST("api/auth/refresh-token")
    suspend fun refreshToken(@Query("token") token: String): LoginResponse

    @POST("/api/password/request-reset")
    suspend fun requestReset(@Query("email") email: String): Response<Unit>

    @POST("/api/password/verify-reset-code")
    suspend fun verifyResetCode(@Query("email") email: String, @Query("resetCode") resetCode: String): Response<Unit>

    @POST("/api/password/reset")
    suspend fun resetPassword(@Query("email") email: String, @Query("newPassword") newPassword: String): Response<Unit>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/api/offers")
    suspend fun getOffers(@Header("Authorization") token: String): List<OfferDTO>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/api/reservations/offer/{offerId}")
    suspend fun getOffersReservations(
        @Header("Authorization") token: String,
        @Path("offerId") offerId: Int
    ): List<ReservationDTO>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/reservations")
    suspend fun createReservation(
        @Header("Authorization") token: String,
        @Body reservationDTO: ReservationDTO
    ): Response<Unit>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("/api/reservations/user/{userId}")
    suspend fun getUsersReservations(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ): List<UserReservationDTO>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @DELETE("/api/reservations/{id}")
    suspend fun deleteReservation(
        @Header("Authorization") token: String,
        @Path("id") reservationId: Int
    ): Response<Unit>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("/api/payments/add")
    suspend fun addPayment(
        @Header("Authorization") token: String,
        @Body payment: Payment
    ): Response<Unit>
}