package pl.meksu.rentcar.domain.model

data class StoredUserData(
    val jwtToken: String? = null,
    val userId: Int? = null,
    val userName: String? = null
)