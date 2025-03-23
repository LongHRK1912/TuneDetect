package com.hrk.tunedetect.setting.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hrk.tunedetect.setting.SettingScreen
import kotlinx.serialization.Serializable

@Serializable
object SettingRoute

fun NavController.navigateToSetting(navOptions: NavOptions? = null) =
    navigate(SettingRoute, navOptions)

fun NavGraphBuilder.settingScreen(
    onNextScreen: () -> Unit,
) {
    composable<SettingRoute> {
        SettingScreen(
            onNextScreen = onNextScreen,
        )
    }
}
