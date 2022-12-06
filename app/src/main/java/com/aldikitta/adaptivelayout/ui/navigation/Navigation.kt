package com.aldikitta.adaptivelayout.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import com.aldikitta.adaptivelayout.ui.navigation.*
import com.aldikitta.adaptivelayout.ui.screens.inbox.InboxScreen
import com.aldikitta.adaptivelayout.ui.screens.inbox.InboxScreen2
import com.aldikitta.adaptivelayout.ui.screens.inbox.InboxViewModel
import com.aldikitta.adaptivelayout.ui.util.*
import kotlinx.coroutines.launch

@Composable
fun Navigation(
    windowSize: WindowSizeClass,
    displayFeatures: List<DisplayFeature>,
) {
    /**
     * This will help us select type of navigation and content type depending on window size and
     * fold state of the device.
     */
    val navigationType: AdaptiveNavigationType
    val contentType: AdaptiveContentType

    /**
     * We are using display's folding features to map the device postures a fold is in.
     * In the state of folding device If it's half fold in BookPosture we want to avoid content
     * at the crease/hinge
     */
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()

    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) ->
            DevicePosture.BookPosture(foldingFeature.bounds)

        isSeparating(foldingFeature) ->
            DevicePosture.Separating(foldingFeature.bounds, foldingFeature.orientation)

        else -> DevicePosture.NormalPosture
    }

    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveContentType.SINGLE_PANE
        }
        WindowWidthSizeClass.Medium -> {
            navigationType = AdaptiveNavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                AdaptiveContentType.DUAL_PANE
            } else {
                AdaptiveContentType.SINGLE_PANE
            }
        }
        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                AdaptiveNavigationType.NAVIGATION_RAIL
            } else {
                AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = AdaptiveContentType.DUAL_PANE
        }
        else -> {
            navigationType = AdaptiveNavigationType.BOTTOM_NAVIGATION
            contentType = AdaptiveContentType.SINGLE_PANE
        }
    }

    /**
     * Content inside Navigation Rail/Drawer can also be positioned at top, bottom or center for
     * ergonomics and reachability depending upon the height of the device.
     */
    val navigationContentPosition = when (windowSize.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            AdaptiveNavigationContentPosition.TOP
        }
        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded -> {
            AdaptiveNavigationContentPosition.CENTER
        }
        else -> {
            AdaptiveNavigationContentPosition.TOP
        }
    }

    AdaptiveNavigationWrapper(
        navigationType = navigationType,
        contentType = contentType,
        displayFeatures = displayFeatures,
        navigationContentPosition = navigationContentPosition,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveNavigationWrapper(
    navigationType: AdaptiveNavigationType,
    contentType: AdaptiveContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: AdaptiveNavigationContentPosition,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val navigationActionEvent = remember(navController) {
        NavigationActionEvent(navController)
    }
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val selectedDestination = navBackStackEntry?.destination?.route ?: AdaptiveRoute.INBOX

    if (navigationType == AdaptiveNavigationType.PERMANENT_NAVIGATION_DRAWER) {
        AdaptivePermanentDrawerContent(
            selectedDestination = selectedDestination,
            navigationDrawerContentPosition = navigationContentPosition,
            navigateToTopLevelDestination = navigationActionEvent::navigateTo,
            content = {
                AdaptiveAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigationActionEvent::navigateTo,
                )
            }
        )
    } else {
        AdaptiveModalDrawerContent(
            selectedDestination = selectedDestination,
            navigationDrawerContentPosition = navigationContentPosition,
            navigateToTopLevelDestination = navigationActionEvent::navigateTo,
            onDrawerClicked = {
                scope.launch {
                    drawerState.close()
                }
            },
            drawerState = drawerState,
            content = {
                AdaptiveAppContent(
                    navigationType = navigationType,
                    contentType = contentType,
                    displayFeatures = displayFeatures,
                    navigationContentPosition = navigationContentPosition,
                    navController = navController,
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigationActionEvent::navigateTo,
                ) {
                    scope.launch {
                        drawerState.open()
                    }
                }
            }
        )
    }
}

@Composable
fun AdaptiveAppContent(
    modifier: Modifier = Modifier,
    navigationType: AdaptiveNavigationType,
    contentType: AdaptiveContentType,
    displayFeatures: List<DisplayFeature>,
    navigationContentPosition: AdaptiveNavigationContentPosition,
    navController: NavHostController,
    selectedDestination: String,
    navigateToTopLevelDestination: (AdaptiveTopLevelDestination) -> Unit,
    onDrawerClicked: () -> Unit = {}
) {
    Row(modifier = modifier.fillMaxSize()) {
        AnimatedVisibility(visible = navigationType == AdaptiveNavigationType.NAVIGATION_RAIL) {
            AdaptiveNavigationRail(
                selectedDestination = selectedDestination,
                navigationRailContentPosition = navigationContentPosition,
                navigateToTopLevelDestination = navigateToTopLevelDestination,
                onDrawerClicked = onDrawerClicked
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {
            AdaptiveNavHost(
                navHostController = navController,
                contentType = contentType,
                displayFeatures = displayFeatures,
                navigationType = navigationType,
                modifier = Modifier.weight(1f),
            )
            AnimatedVisibility(visible = navigationType == AdaptiveNavigationType.BOTTOM_NAVIGATION) {
                AdaptiveNavigationBar(
                    selectedDestination = selectedDestination,
                    navigateToTopLevelDestination = navigateToTopLevelDestination
                )
            }
        }
    }
}

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AdaptiveNavHost(
    navHostController: NavHostController,
    contentType: AdaptiveContentType,
    displayFeatures: List<DisplayFeature>,
    navigationType: AdaptiveNavigationType,
    modifier: Modifier = Modifier,
) {
    val inboxViewModel: InboxViewModel = viewModel()
    val replyHomeUIState by inboxViewModel.uiState.collectAsStateWithLifecycle()
    NavHost(
        modifier = modifier,
        navController = navHostController,
        startDestination = AdaptiveRoute.INBOX
    ) {
        composable(AdaptiveRoute.INBOX) {
            InboxScreen2(
                contentType = contentType,
                navigationType = navigationType,
                displayFeatures = displayFeatures,
            )

        }
        composable(AdaptiveRoute.ARTICLES) {

        }
    }
}