package com.hrk.apps.hrkdev.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.ui.HRKAppState
import com.hrk.tunedetect.home.navigation.HomeRoute
import com.hrk.tunedetect.home.navigation.homeScreen
import com.hrk.tunedetect.home.navigation.navigateToHomeScreen
import com.hrk.tunedetect.result.navigation.ResultRoute
import com.hrk.tunedetect.result.navigation.resultScreen
import com.hrk.tunedetect.setting.navigation.settingScreen
import com.hrk.tunedetect.splash.navigation.SplashRoute
import com.hrk.tunedetect.splash.navigation.navigateToSplashScreen
import com.hrk.tunedetect.splash.navigation.splashScreen

@Composable
fun HRKNavHost(
    modifier: Modifier,
    tuneDetectViewModel: TuneDetectViewModel,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = ResultRoute,
    ) {
        splashScreen(
            onNextScreen = navController::navigateToHomeScreen,
        )

        homeScreen(
            onNextScreen = {},
            tuneDetectViewModel = tuneDetectViewModel
        )

        resultScreen(
            onBack = navController::popBackStack,
            navController = navController
        )

        settingScreen(
            onNextScreen = navController::navigateToSplashScreen,
        )
    }
}
