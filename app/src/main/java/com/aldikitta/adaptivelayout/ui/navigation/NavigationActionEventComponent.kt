package com.aldikitta.adaptivelayout.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aldikitta.adaptivelayout.ui.util.AdaptiveNavigationContentPosition

@Composable
fun AdaptiveNavigationRail(
    selectedDestination: String,
    navigationRailContentPosition: AdaptiveNavigationContentPosition,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationRail() {
        TOP_LEVEL_DESTINATIONS.forEachIndexed { _, adaptiveTopLevelDestination ->
            NavigationRailItem(
                selected = selectedDestination == adaptiveTopLevelDestination.route,
                onClick = { navigateToTopLevelDestination(adaptiveTopLevelDestination) },
                icon = {
                    Icon(
                        imageVector = adaptiveTopLevelDestination.selectedIcon,
                        contentDescription = stringResource(
                            id = adaptiveTopLevelDestination.iconTextId
                        )
                    )
                }
            )
        }
    }
}

@Composable
fun AdaptiveNavigationBar(
    selectedDestination: String,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
) {
    NavigationBar() {
        TOP_LEVEL_DESTINATIONS.forEachIndexed { _, adaptiveTopLevelDestination ->
            NavigationBarItem(
                selected = selectedDestination == adaptiveTopLevelDestination.route,
                onClick = { navigateToTopLevelDestination(adaptiveTopLevelDestination) },
                icon = {
                    Icon(
                        imageVector = adaptiveTopLevelDestination.selectedIcon,
                        contentDescription = stringResource(id = adaptiveTopLevelDestination.iconTextId)
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptivePermanentDrawerContent(
    selectedDestination: String,
    navigationDrawerContentPosition: AdaptiveNavigationContentPosition,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
    content:  @Composable () -> Unit = {}
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.sizeIn(
                    minWidth = 200.dp, maxWidth = 300.dp
                )
            ) {
                TOP_LEVEL_DESTINATIONS.forEach { adaptiveTopLevelDestination ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(horizontal = 12.dp),
                        label = { Text(text = stringResource(id = adaptiveTopLevelDestination.iconTextId)) },
                        selected = selectedDestination == adaptiveTopLevelDestination.route,
                        onClick = { navigateToTopLevelDestination(adaptiveTopLevelDestination) },
                        icon = {
                            Icon(
                                imageVector = adaptiveTopLevelDestination.selectedIcon,
                                contentDescription = stringResource(
                                    id = adaptiveTopLevelDestination.iconTextId
                                )
                            )
                        }
                    )
                }
            }
        },
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveModalDrawerContent(
    selectedDestination: String,
    navigationDrawerContentPosition: AdaptiveNavigationContentPosition,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {},
    drawerState: DrawerState,
    content:  @Composable () -> Unit = {}
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet() {
                TOP_LEVEL_DESTINATIONS.forEach { adaptiveTopLevelDestination ->
                    NavigationDrawerItem(
                        label = { Text(text = stringResource(id = adaptiveTopLevelDestination.iconTextId)) },
                        selected = selectedDestination == adaptiveTopLevelDestination.route,
                        onClick = { navigateToTopLevelDestination(adaptiveTopLevelDestination) },
                        icon = {
                            Icon(
                                imageVector = adaptiveTopLevelDestination.selectedIcon,
                                contentDescription = stringResource(
                                    id = adaptiveTopLevelDestination.iconTextId
                                )
                            )
                        }
                    )
                }
            }
        },
        drawerState = drawerState,
        content = content
    )
}