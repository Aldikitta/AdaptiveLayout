package com.aldikitta.adaptivelayout.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.window.layout.DisplayFeature
import com.aldikitta.inbox.InboxScreen
import com.aldikitta.mail.MailScreen
import com.aldikitta.ui.AdaptiveContentType
import com.aldikitta.ui.AdaptiveNavigationType

@Composable
fun AdaptiveNavHost(
    navHostController: NavHostController,
    contentType: AdaptiveContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: AdaptiveNavigationType,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = AdaptiveRoute.INBOX
    ) {
        composable(AdaptiveRoute.INBOX) {
            InboxScreen(
                contentType = contentType,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
            )
        }
        composable(AdaptiveRoute.ARTICLES) {
            MailScreen()
        }
    }
}