package com.aldikitta.adaptivelayout.ui.screens.inbox.first_pane

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.aldikitta.adaptivelayout.data.model.Email
import com.aldikitta.adaptivelayout.ui.composable.AdaptiveSearchBar
import com.aldikitta.adaptivelayout.ui.composable.ReplyEmailListItem
import com.aldikitta.adaptivelayout.ui.util.AdaptiveContentType

@Composable
fun ReplyEmailList(
    emails: List<Email>,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long, AdaptiveContentType) -> Unit
) {
    LazyColumn(modifier = modifier, state = emailLazyListState) {
        item {
            AdaptiveSearchBar(modifier = Modifier.fillMaxWidth())
        }
        items(items = emails, key = { it.id }) { email ->
            ReplyEmailListItem(email = email) { emailId ->
                navigateToDetail(emailId, AdaptiveContentType.SINGLE_PANE)
            }
        }
    }
}