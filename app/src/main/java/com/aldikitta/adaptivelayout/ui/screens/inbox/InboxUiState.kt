package com.aldikitta.adaptivelayout.ui.screens.inbox

import androidx.compose.runtime.Stable
import com.aldikitta.adaptivelayout.data.model.Email

//data class InboxUiState(
//    val emails: List<Email> = emptyList(),
//    val selectedEmail: Email? = null,
//    val isDetailOnlyOpen: Boolean = false,
//    val loading: Boolean = false,
//    val error: String? = null
//)

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