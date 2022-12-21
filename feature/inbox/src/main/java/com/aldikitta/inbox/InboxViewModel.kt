package com.aldikitta.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.data.repository.EmailsRepository
import com.aldikitta.data.repository.EmailsRepositoryImpl
import com.aldikitta.ui.AdaptiveContentType
import kotlinx.coroutines.flow.*

class InboxViewModel(
    private val emailsRepository: EmailsRepository = EmailsRepositoryImpl(),
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState1: MutableStateFlow<InboxUiState> = MutableStateFlow(InboxUiState.default)
    val uiState1 = _uiState1.asStateFlow()

    private val _uiState: MutableStateFlow<InboxUiState> = MutableStateFlow(InboxUiState.default)
    val uiState = _uiState
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            InboxUiState.default
        )

    init {
        obServeAEmails()
    }

    private fun obServeAEmails() {
        emailsRepository.getAllEmails()
            .catch {
                _uiState.update { inboxUiState ->
                    inboxUiState.copy(
                        error = it.message
                    )
                }
            }
            .onEach {
                /**
                 * We set first email selected by default for first App launch in large-screens
                 */
                _uiState.update { inboxUiState ->
                    inboxUiState.copy(
                        emails = it,
                        selectedEmail = it.first()
                    )
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(inboxUiEvent: InboxUiEvent) {
        when (inboxUiEvent) {
            is InboxUiEvent.SetSelectedEmails -> {
                /**
                 * We only set isDetailOnlyOpen to true when it's only single pane layout
                 */
                val email = _uiState.value.emails.find { email ->
                    email.id == inboxUiEvent.emailId
                }

                _uiState.update { inboxUiState ->
                    inboxUiState.copy(
                        selectedEmail = email,
                        isDetailOnlyOpen = inboxUiEvent.contentType == AdaptiveContentType.SINGLE_PANE
                    )
                }
            }
            is InboxUiEvent.CloseDetailScreen -> {
                _uiState.update { inboxUiState ->
                    inboxUiState.copy(
                        isDetailOnlyOpen = false,
                        selectedEmail = _uiState.value.emails.first()
                    )
                }
            }
        }
    }
}

