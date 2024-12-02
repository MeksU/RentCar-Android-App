package pl.meksu.rentcar.data.remote.model

data class LoginResponse(
    val jwt: String,
    val userId: Int,
    val userName: String,
    val userType: String
)