package com.aldikitta.adaptivelayout.ui.screens.inbox

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aldikitta.adaptivelayout.data.EmailsRepository
import com.aldikitta.adaptivelayout.data.EmailsRepositoryImpl
import com.aldikitta.adaptivelayout.ui.util.AdaptiveContentType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class InboxViewModel(
    private val emailsRepository: EmailsRepository = EmailsRepositoryImpl()
) : ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(InboxUiState(loading = true))
    val uiState: StateFlow<InboxUiState> = _uiState

    init {
        obServeAEmails()
    }

    private fun obServeAEmails() {
        viewModelScope.launch {
            emailsRepository.getAllEmails()
                .catch {
                    _uiState.value = InboxUiState(error = it.message)
                }
                .collect {
                    /**
                     * We set first email selected by default for first App launch in large-screens
                     */
                    _uiState.value = InboxUiState(
                        emails = it,
                        selectedEmail = it.first()
                    )
                }
        }
    }

    fun onEvent(inboxUiEvent: InboxUiEvent) {
        when (inboxUiEvent) {
            is InboxUiEvent.SetSelectedEmails -> {
                /**
                 * We only set isDetailOnlyOpen to true when it's only single pane layout
                 */
                val email = uiState.value.emails.find { email ->
                    email.id == inboxUiEvent.emailId
                }

                _uiState.value = _uiState.value.copy(
                    selectedEmail = email,
                    isDetailOnlyOpen = inboxUiEvent.contentType == AdaptiveContentType.SINGLE_PANE
                )
            }
            is InboxUiEvent.CloseDetailScreen -> {
                _uiState.value = _uiState.value.copy(
                    isDetailOnlyOpen = false,
                    selectedEmail = _uiState.value.emails.first()
                )
            }
        }
    }
}

