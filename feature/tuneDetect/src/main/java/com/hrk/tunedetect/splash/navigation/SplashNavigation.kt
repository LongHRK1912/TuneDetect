package com.hrk.tunedetect.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hrk.tunedetect.splash.SplashScreenScreen
import kotlinx.serialization.Serializable

@Serializable
object SplashRoute

fun NavController.navigateToSplashScreen(navOptions: NavOptions? = null) =
    navigate(SplashRoute, navOptions)

fun NavGraphBuilder.splashScreen(
    onNextScreen: () -> Unit
) {
    composable<SplashRoute> {
        SplashScreenScreen(
            onFinished = onNextScreen
        )
    }
}
