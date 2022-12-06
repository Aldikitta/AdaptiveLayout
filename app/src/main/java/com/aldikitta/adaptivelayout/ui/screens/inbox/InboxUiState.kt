package com.aldikitta.adaptivelayout.ui.screens.inbox

import com.aldikitta.adaptivelayout.data.Email

data class InboxUiState(
    val emails: List<Email> = emptyList(),
    val selectedEmail: Email? = null,
    val isDetailOnlyOpen: Boolean = false,
    val loading: Boolean = false,
    val error: String? = null
)