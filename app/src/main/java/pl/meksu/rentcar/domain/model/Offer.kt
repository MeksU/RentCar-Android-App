package pl.meksu.rentcar.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Offer(
    val id: Int,
    val car: Car,
    val price: Double,
    val description: String,
    val promotion: Double
): Parcelable