package pl.meksu.rentcar.data.datastore

data class StoredUserData(
    val jwtToken: String? = null,
    val userId: Int? = null,
    val userName: String? = null
)