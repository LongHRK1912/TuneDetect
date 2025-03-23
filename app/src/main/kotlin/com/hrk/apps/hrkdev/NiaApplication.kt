package com.hrk.apps.hrkdev

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HRKApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}
