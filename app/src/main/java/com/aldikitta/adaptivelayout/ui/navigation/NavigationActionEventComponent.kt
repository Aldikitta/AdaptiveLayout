package com.aldikitta.adaptivelayout.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MenuOpen
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.offset
import com.aldikitta.adaptivelayout.R
import com.aldikitta.adaptivelayout.ui.util.AdaptiveNavigationContentPosition

enum class LayoutType {
    HEADER, CONTENT
}

@Composable
fun AdaptiveNavigationRail(
    selectedDestination: String,
    navigationRailContentPosition: AdaptiveNavigationContentPosition,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    NavigationRail(
        modifier = Modifier.fillMaxHeight(),
        containerColor = MaterialTheme.colorScheme.inverseOnSurface,
        header = {
            NavigationRailItem(
                selected = false,
                onClick = onDrawerClicked,
                icon = {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = stringResource(id = R.string.navigation_drawer)
                    )
                }
            )
            FloatingActionButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.padding(bottom = 32.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(id = com.aldikitta.adaptivelayout.R.string.edit),
                )
            }
        }
    ) {
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
                },
                label = { Text(text = stringResource(id = adaptiveTopLevelDestination.iconTextId)) },

                alwaysShowLabel = false
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
    content: @Composable () -> Unit = {}
) {
    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet(
                modifier = Modifier.sizeIn(
                    minWidth = 200.dp, maxWidth = 300.dp
                ),
                drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp),
                    text = stringResource(id = R.string.app_name).uppercase(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                ExtendedFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 40.dp, start = 16.dp, end = 16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.compose),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
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
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
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
    content: @Composable () -> Unit = {}
) {
    ModalNavigationDrawer(
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.inverseOnSurface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.app_name).uppercase(),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onDrawerClicked) {
                        Icon(
                            imageVector = Icons.Default.MenuOpen,
                            contentDescription = stringResource(id = R.string.navigation_drawer)
                        )
                    }
                }
                ExtendedFloatingActionButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 40.dp, start = 16.dp, end = 16.dp),
                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                    contentColor = MaterialTheme.colorScheme.onTertiaryContainer
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(id = R.string.edit),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.compose),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
                TOP_LEVEL_DESTINATIONS.forEach { adaptiveTopLevelDestination ->
                    NavigationDrawerItem(
                        modifier = Modifier.padding(horizontal = 16.dp),
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
                        },
                        colors = NavigationDrawerItemDefaults.colors(
                            unselectedContainerColor = Color.Transparent
                        ),
                    )
                }

            }
        },
        drawerState = drawerState,
        content = content
    )
}