package com.aldikitta.adaptivelayout.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.aldikitta.adaptivelayout.R

object AdaptiveRoute {
    const val INBOX = "inbox"
    const val ARTICLES = "articles"
}

data class AdaptiveTopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val iconTextId: Int
)

class NavigationActionEvent(
    private val navController: NavHostController
) {
    fun navigateTo(destination: AdaptiveTopLevelDestination) {
        navController.navigate(destination.route) {
            // Pop up to the start destination of the graph to
            // avoid building up a large stack of destinations
            // on the back stack as users select items
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            // Avoid multiple copies of the same destination when
            // reselecting the same item
            launchSingleTop = true
            // Restore state when reselecting a previously selected item
            restoreState = true
        }
    }
}

val TOP_LEVEL_DESTINATIONS = listOf(
    AdaptiveTopLevelDestination(
        route = AdaptiveRoute.INBOX,
        selectedIcon = Icons.Default.Inbox,
        unSelectedIcon = Icons.Default.Inbox,
        iconTextId = R.string.tab_inbox
    ),
    AdaptiveTopLevelDestination(
        route = AdaptiveRoute.ARTICLES,
        selectedIcon = Icons.Default.Article,
        unSelectedIcon = Icons.Default.Article,
        iconTextId = R.string.tab_article
    ),
)