package pl.meksu.rentcar.data.remote.model

data class RegisterRequest(
    val address: String,
    val mail: String,
    val name: String,
    val password: String,
    val phone: String,
    val postalCode: String,
    val surname: String
)