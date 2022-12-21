package com.aldikitta.inbox

import androidx.compose.runtime.Stable
import com.aldikitta.data.model.Email

@Stable
data class InboxUiState(
    val emails: List<Email>,
    val selectedEmail: Email?,
    val isDetailOnlyOpen: Boolean,
    val loading: Boolean,
    val error: String?
){
    companion object{
        val default: InboxUiState = InboxUiState(
            emails = emptyList(),
            selectedEmail = null,
            isDetailOnlyOpen = false,
            loading = false,
            error = null
        )
    }
}