package com.hrk.apps.hrkdev.handler

import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface ACRCloudEntryPoint {
    fun acrCloudClient(): ACRCloudClient
    fun acrCloudConfig(): ACRCloudConfig
}