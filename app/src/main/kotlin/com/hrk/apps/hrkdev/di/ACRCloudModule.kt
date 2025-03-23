package com.hrk.apps.hrkdev.di

import android.content.Context
import com.acrcloud.rec.ACRCloudClient
import com.acrcloud.rec.ACRCloudConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ACRCloudModule {

    @Provides
    fun provideACRCloudConfig(@ApplicationContext context: Context): ACRCloudConfig {
        return ACRCloudConfig().apply {
            this.context = context
            host = "identify-ap-southeast-1.acrcloud.com"
            accessKey = "c38a8e285089e689e5abb33385e8bea2"
            accessSecret = "eRHYxaSaMLV6zIgehvsIqnqgGE3w4HX5i369e8Yg"
            recorderConfig.isVolumeCallback = true
        }
    }

    @Provides
    @Singleton
    fun provideACRCloudClient(config: ACRCloudConfig): ACRCloudClient {
        return ACRCloudClient().apply {
            initWithConfig(config)
        }
    }
}