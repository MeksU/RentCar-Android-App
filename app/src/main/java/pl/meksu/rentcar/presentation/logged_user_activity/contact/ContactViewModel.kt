package pl.meksu.rentcar.presentation.logged_user_activity.contact

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.meksu.rentcar.common.Resource
import pl.meksu.rentcar.domain.datastore.EncryptedDataStore
import pl.meksu.rentcar.domain.model.UserMessage
import pl.meksu.rentcar.domain.use_case.SendMessageUseCase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class ContactViewModel @Inject constructor(
    private val encryptedDataStore: EncryptedDataStore,
    private val sendMessageUseCase: SendMessageUseCase
): ViewModel() {
    private val _state = mutableStateOf(ContactState())
    val state: State<ContactState> = _state
    private val _title = mutableStateOf("")
    val title: State<String> = _title
    private val _message = mutableStateOf("")
    val message: State<String> = _message

    fun updateTitle(title: String) {
        _title.value = title
    }

    fun updateMessage(message: String) {
        _message.value = message
    }

    fun sendMessage() {
        viewModelScope.launch {
            val userData = withContext(Dispatchers.IO) {
                encryptedDataStore.loadUserData()
            }
            val token = "Bearer ${userData.jwtToken}"
            val userId: Int = userData.userId ?: -1
            val timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            val userMessage = UserMessage(
                user = userId,
                title = _title.value,
                content = _message.value,
                sentDate = timestamp
            )
            sendMessageUseCase(token, userMessage).collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.value = ContactState(success = true)
                        _title.value = ""
                        _message.value = ""
                    }
                    is Resource.Error -> {
                        _state.value = ContactState(error = result.message ?: "An unexpected error occurred.")
                    }
                    is Resource.Loading -> {
                        _state.value = ContactState(isLoading = true)
                    }
                }
            }
        }
    }
}