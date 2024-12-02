package pl.meksu.rentcar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Car(
    val id: Int,
    val image: String,
    val model: String,
    val brand: String,
    val power: Int,
    val seats: Int,
    val type: String,
    val engine: String,
    val fuelType: String,
    val transmission: String,
): Parcelable