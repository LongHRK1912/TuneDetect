package com.hrk.apps.hrkdev.initializer

import android.content.Context
import androidx.startup.Initializer
import com.hrk.preferences.initializer.PreferencesInitializer

class AppInitializer : Initializer<Unit> {

    override fun create(context: Context) = Unit

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        PreferencesInitializer::class.java,
    )
}
