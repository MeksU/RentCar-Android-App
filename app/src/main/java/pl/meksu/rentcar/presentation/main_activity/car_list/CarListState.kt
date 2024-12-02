package pl.meksu.rentcar.presentation.main_activity.car_list

import pl.meksu.rentcar.domain.model.Car

data class CarListState(
    val isLoading: Boolean = false,
    val cars: List<Car> = emptyList(),
    val error: String = ""
)
