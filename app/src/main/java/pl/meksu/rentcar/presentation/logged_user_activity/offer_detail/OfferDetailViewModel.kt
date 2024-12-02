package pl.meksu.rentcar.presentation.logged_user_activity.offer_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.model.Reservation
import pl.meksu.rentcar.domain.use_case.OfferReservationsUseCase
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class OfferDetailViewModel @Inject constructor(
    private val offerReservationsUseCase: OfferReservationsUseCase,
    private val encryptedDataStore: EncryptedDataStore
): ViewModel() {
    private val _state = mutableStateOf(OfferDetailState())
    val state: State<OfferDetailState> = _state

    private val _createState = mutableStateOf(CreateReservationState())
    val createState: State<CreateReservationState> = _createState

    fun fetchReservations(offerId: Int) {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"
            offerReservationsUseCase(token, offerId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = OfferDetailState(reservations = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = OfferDetailState(
                            error = result.message ?: "An unexpected error occurred."
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = OfferDetailState(isLoading = true)
                    }
                }
            }
        }
    }

    fun createReservation(startDate: LocalDate, endDate: LocalDate, offerId: Int) {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"
            val userId = userData.userId ?: -1
            val reservation = Reservation(userId, offerId, startDate, endDate)

            offerReservationsUseCase(token, reservation).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _createState.value = CreateReservationState(success = result.data ?: "")
                    }
                    is Resource.Error -> {
                        _createState.value = CreateReservationState(error = result.message ?: "An unexpected error occurred.")
                    }
                    is Resource.Loading -> {
                        _createState.value = CreateReservationState(isLoading = true)
                    }
                }
            }
        }
    }

    fun clearCreateState() {
        _createState.value = CreateReservationState()
    }
}