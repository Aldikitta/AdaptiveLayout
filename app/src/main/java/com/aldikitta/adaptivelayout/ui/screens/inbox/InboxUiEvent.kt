package com.aldikitta.adaptivelayout.ui.screens.inbox

import com.aldikitta.adaptivelayout.ui.util.AdaptiveContentType

sealed class InboxUiEvent {
    data class SetSelectedEmails(val emailId: Long, val contentType: AdaptiveContentType) : InboxUiEvent()
    object CloseDetailScreen : InboxUiEvent()
}