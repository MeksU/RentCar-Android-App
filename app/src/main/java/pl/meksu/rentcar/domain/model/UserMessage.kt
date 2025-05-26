package pl.meksu.rentcar.domain.model

data class UserMessage(
    val user: Int,
    val title: String,
    val content: String,
    val sentDate: String
)