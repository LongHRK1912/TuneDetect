package com.hrk.apps.hrkdev

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class HRKApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
