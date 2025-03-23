package com.hrk.apps.hrkdev.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.ui.HRKAppState
import com.hrk.tunedetect.home.navigation.HomeRoute
import com.hrk.tunedetect.home.navigation.homeScreen
import com.hrk.tunedetect.home.navigation.navigateToHomeScreen
import com.hrk.tunedetect.setting.navigation.settingScreen
import com.hrk.tunedetect.splash.navigation.navigateToSplashScreen
import com.hrk.tunedetect.splash.navigation.splashScreen

@Composable
fun HRKNavHost(
    modifier: Modifier,
    appState: HRKAppState,
    tuneDetectViewModel: TuneDetectViewModel,
) {
    val navController = appState.navController
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeRoute,
    ) {
        splashScreen(
            onNextScreen = navController::navigateToHomeScreen,
        )

        homeScreen(
            onNextScreen = {},
            tuneDetectViewModel = tuneDetectViewModel
        )

        settingScreen(
            onNextScreen = navController::navigateToSplashScreen,
        )
    }
}
