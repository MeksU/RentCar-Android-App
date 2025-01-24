package pl.meksu.rentcar.presentation.logged_user_activity.reservations

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.use_case.DeleteReservationUseCase
import pl.meksu.rentcar.domain.use_case.GetUserReservationsUseCase
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val getUserReservationsUseCase: GetUserReservationsUseCase,
    private val deleteReservationUseCase: DeleteReservationUseCase,
    private val encryptedDataStore: EncryptedDataStore
): ViewModel() {
    private val _state = mutableStateOf(ReservationState())
    val state: State<ReservationState> = _state

    private val _createState = mutableStateOf(DeleteReservationState())
    val deleteState: State<DeleteReservationState> = _createState

    init {
        fetchReservations()
    }

    private fun fetchReservations() {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"
            val userId = userData.userId ?: -1
            getUserReservationsUseCase(token, userId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = ReservationState(reservations = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = ReservationState(error = result.message ?: "An unexpected error occurred.")
                    }
                    is Resource.Loading -> {
                        _state.value = ReservationState(isLoading = true)
                    }
                }
            }
        }
    }

    fun deleteReservation(offerId: Int) {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"

            deleteReservationUseCase(token, offerId).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _createState.value = DeleteReservationState(success = result.data ?: "")
                    }
                    is Resource.Error -> {
                        _createState.value = DeleteReservationState(error = result.message ?: "An unexpected error occurred.")
                    }
                    is Resource.Loading -> {
                        _createState.value = DeleteReservationState(isLoading = true)
                    }
                }
            }
            fetchReservations()
        }
    }

    fun clearDeleteState() {
        _createState.value = DeleteReservationState()
    }
}