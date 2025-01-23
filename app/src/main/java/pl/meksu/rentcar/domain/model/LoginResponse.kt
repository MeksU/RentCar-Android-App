package pl.meksu.rentcar.domain.model

data class LoginResponse(
    val jwt: String,
    val userId: Int,
    val userName: String,
    val userType: String
)