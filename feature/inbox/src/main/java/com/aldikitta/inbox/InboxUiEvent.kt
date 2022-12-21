package com.aldikitta.inbox

import com.aldikitta.ui.AdaptiveContentType

sealed class InboxUiEvent {
    data class SetSelectedEmails(val emailId: Long, val contentType: AdaptiveContentType) : InboxUiEvent()
    object CloseDetailScreen : InboxUiEvent()
}