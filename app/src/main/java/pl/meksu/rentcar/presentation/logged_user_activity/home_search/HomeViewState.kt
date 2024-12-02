package pl.meksu.rentcar.presentation.logged_user_activity.home_search

import pl.meksu.rentcar.domain.model.Offer

data class HomeViewState(
    val isLoading: Boolean = false,
    val offers: List<Offer> = emptyList(),
    val error: String = ""
)
