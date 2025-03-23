/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrk.apps.hrkdev

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import com.acrcloud.rec.ACRCloudResult
import com.acrcloud.rec.IACRCloudListener
import com.hrk.apps.hrkdev.core.data.TuneDetectViewModel
import com.hrk.apps.hrkdev.core.data.util.NetworkMonitor
import com.hrk.apps.hrkdev.core.model.iacr_cloud.ACRCloudResponse
import com.hrk.apps.hrkdev.core.model.iacr_cloud.IACRCloudState
import com.hrk.apps.hrkdev.core.utils.JSON.decodeTo
import com.hrk.apps.hrkdev.core.utils.JSON.toJson
import com.hrk.apps.hrkdev.handler.ACRCloudEntryPoint
import com.hrk.apps.hrkdev.ui.HRKApp
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), IACRCloudListener {
    @Inject
    lateinit var networkMonitor: NetworkMonitor

    @Inject
    lateinit var tuneDetectViewModel: TuneDetectViewModel

    private lateinit var acrCloudClient: ACRCloudClient
    private lateinit var acrCloudConfig: ACRCloudConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HRKApp(
                networkMonitor = networkMonitor,
                tuneDetectViewModel = tuneDetectViewModel,
                acrCloudClient = acrCloudClient
            )
        }
        val entryPoint = EntryPointAccessors.fromActivity(this, ACRCloudEntryPoint::class.java)
        acrCloudClient = entryPoint.acrCloudClient()
        acrCloudConfig = entryPoint.acrCloudConfig()
        acrCloudConfig.acrcloudListener = this
        tuneDetectViewModel.updateServiceInitialized(acrCloudClient.initWithConfig(acrCloudConfig))
    }

    override fun onResult(result: ACRCloudResult?) {
        result?.result?.decodeTo(ACRCloudResponse::class.java)?.let { response ->
            tuneDetectViewModel.updateStateIACRCloud(IACRCloudState.Success(response))
        }
    }

    override fun onVolumeChanged(volume: Double) {
        tuneDetectViewModel.onVolumeChanged(volume)
    }

    override fun onDestroy() {
        super.onDestroy()
        this.acrCloudClient.release()
    }
}