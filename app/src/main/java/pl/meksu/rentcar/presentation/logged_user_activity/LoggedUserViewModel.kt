package pl.meksu.rentcar.presentation.logged_user_activity

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.model.Payment
import pl.meksu.rentcar.domain.use_case.AddPaymentUseCase
import javax.inject.Inject

@HiltViewModel
class LoggedUserViewModel @Inject constructor(
    private val encryptedDataStore: EncryptedDataStore,
    private val addPaymentUseCase: AddPaymentUseCase
): ViewModel() {
    private val _navigateToReservations = mutableStateOf(false)
    val navigateToReservations: State<Boolean> get() = _navigateToReservations

    private val _reservationId = mutableIntStateOf(0)

    fun addPayment(paymentNumber: String) {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"
            val payment = Payment(_reservationId.intValue, paymentNumber)
            addPaymentUseCase(token, payment).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _navigateToReservations.value = true
                    }

                    is Resource.Error -> {
                        _navigateToReservations.value = true
                    }

                    is Resource.Loading -> {
                        _navigateToReservations.value = true
                    }
                }
            }
        }
    }

    fun logOut() {
        viewModelScope.launch {
            encryptedDataStore.clearUserData()
        }
    }

    fun setNavigateToReservations() {
        _navigateToReservations.value = true
    }

    fun resetNavigation() {
        _navigateToReservations.value = false
    }

    fun setReservationId(id: Int) {
        _reservationId.intValue = id
    }
}