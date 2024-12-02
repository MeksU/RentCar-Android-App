package pl.meksu.rentcar.presentation.logged_user_activity.home_search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.data.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.use_case.GetOffersUseCase
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getOffersUseCase: GetOffersUseCase,
    private val encryptedDataStore: EncryptedDataStore
): ViewModel() {
    private val _state = mutableStateOf(HomeViewState())
    val state: State<HomeViewState> = _state
    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    init {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            _userName.value = userData.userName ?: ""
        }
        fetchOffers()
    }

    private fun fetchOffers() {
        viewModelScope.launch {
            val userData = encryptedDataStore.loadUserData()
            val token = "Bearer ${userData.jwtToken}"
            getOffersUseCase(token).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = HomeViewState(offers = result.data ?: emptyList())
                    }
                    is Resource.Error -> {
                        _state.value = HomeViewState(
                            error = result.message ?: "An unexpected error occurred."
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = HomeViewState(isLoading = true)
                    }
                }
            }
        }
    }
}