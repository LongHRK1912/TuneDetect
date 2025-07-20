package com.hrk.tunedetect.result.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.tunedetect.result.ResultScreen
import kotlinx.serialization.Serializable

@Serializable
object ResultRoute

fun NavController.navigateToResultScreen(navOptions: NavOptions? = null) = navigate(ResultRoute, navOptions)

fun NavGraphBuilder.resultScreen(
    onBack: () -> Unit
) {
    composable<ResultRoute> {
        ResultScreen(
            onBack = onBack,
            acrCloud = ACRCloudResponse()
        )
    }
}