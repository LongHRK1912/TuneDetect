package com.hrk.tunedetect.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.tunedetect.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

fun NavController.navigateToHomeScreen(navOptions: NavOptions? = null) =
    navigate(HomeRoute, navOptions)

fun NavGraphBuilder.homeScreen(
    onNextScreen: () -> Unit,
    tuneDetectViewModel: TuneDetectViewModel
) {
    composable<HomeRoute> {
        HomeScreen(
            tuneDetectViewModel = tuneDetectViewModel
        )
    }
}