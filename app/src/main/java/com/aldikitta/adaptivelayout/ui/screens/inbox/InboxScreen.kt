package com.aldikitta.adaptivelayout.ui.screens.inbox

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.window.layout.DisplayFeature
import com.aldikitta.adaptivelayout.ui.screens.inbox.first_pane.ReplyEmailList
import com.aldikitta.adaptivelayout.ui.screens.inbox.second_pane.ReplyEmailDetail
import com.aldikitta.adaptivelayout.ui.util.AdaptiveContentType
import com.aldikitta.adaptivelayout.ui.util.AdaptiveNavigationType
import com.google.accompanist.adaptive.HorizontalTwoPaneStrategy
import com.google.accompanist.adaptive.TwoPane

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun InboxScreen(
    contentType: AdaptiveContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: AdaptiveNavigationType,
    inboxViewModel: InboxViewModel = viewModel()
) {
    val replyHomeUIState by inboxViewModel.uiState.collectAsStateWithLifecycle()
    InboxScreenContent(
        contentType = contentType,
        replyHomeUIState = replyHomeUIState,
        navigationType = navigationType,
        displayFeatures = displayFeatures,
        closeDetailScreen = {
            inboxViewModel.onEvent(InboxUiEvent.CloseDetailScreen)
        },
        navigateToDetail = {emailId, pane ->
            inboxViewModel.onEvent(InboxUiEvent.SetSelectedEmails(emailId = emailId, contentType = pane))
        }
    )
}


@Composable
fun InboxScreenContent(
    contentType: AdaptiveContentType,
    replyHomeUIState: InboxUiState,
    navigationType: AdaptiveNavigationType,
    displayFeatures: List<DisplayFeature>,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, AdaptiveContentType) -> Unit,
    modifier: Modifier = Modifier,
) {
//    val replyHomeUIState by inboxViewModel.uiState.collectAsStateWithLifecycle(InboxUiState())
    /**
     * When moving from LIST_AND_DETAIL page to LIST page clear the selection and user should see LIST screen.
     */
    LaunchedEffect(key1 = contentType) {
        if (contentType == AdaptiveContentType.SINGLE_PANE && !replyHomeUIState.isDetailOnlyOpen) {
            closeDetailScreen()
        }
    }

    val emailLazyListState = rememberLazyListState()

    if (contentType == AdaptiveContentType.DUAL_PANE) {
        TwoPane(
            first = {
                ReplyEmailList(
                    emails = replyHomeUIState.emails,
                    emailLazyListState = emailLazyListState,
                    navigateToDetail = navigateToDetail
                )
            },
            second = {
                ReplyEmailDetail(
                    email = replyHomeUIState.selectedEmail ?: replyHomeUIState.emails.first(),
                    isFullScreen = false
                )
            },
            strategy = HorizontalTwoPaneStrategy(splitFraction = 0.5f, gapWidth = 16.dp),
            displayFeatures = displayFeatures
        )
    } else {
        Box(modifier = modifier.fillMaxSize()) {
            ReplySinglePaneContent(
                replyHomeUIState = replyHomeUIState,
                emailLazyListState = emailLazyListState,
                modifier = Modifier.fillMaxSize(),
                closeDetailScreen = closeDetailScreen,
                navigateToDetail = navigateToDetail
            )
            // When we have bottom navigation we show FAB at the bottom end.
            if (navigationType == AdaptiveNavigationType.BOTTOM_NAVIGATION) {
                FloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = com.aldikitta.adaptivelayout.R.string.edit),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ReplySinglePaneContent(
    replyHomeUIState: InboxUiState,
    emailLazyListState: LazyListState,
    modifier: Modifier = Modifier,
    closeDetailScreen: () -> Unit,
    navigateToDetail: (Long, AdaptiveContentType) -> Unit
) {
    if (replyHomeUIState.selectedEmail != null && replyHomeUIState.isDetailOnlyOpen) {
        BackHandler {
            closeDetailScreen()
        }
        ReplyEmailDetail(email = replyHomeUIState.selectedEmail) {
            closeDetailScreen()
        }
    } else {
        ReplyEmailList(
            emails = replyHomeUIState.emails,
            emailLazyListState = emailLazyListState,
            modifier = modifier,
            navigateToDetail = navigateToDetail
        )
    }
}