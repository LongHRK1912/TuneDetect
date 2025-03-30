package com.hrk.preferences.initializer

import android.content.Context
import androidx.startup.Initializer
import com.hrk.apps.hrkdev.core.utils.AuthService
import com.hrk.apps.hrkdev.core.utils.AuthSpotifyResponse
import com.hrk.apps.hrkdev.core.utils.JSON.decodeTo
import com.hrk.preferences.Preferences
import com.hrk.preferences.di.PreferencesEntryPoint
import javax.inject.Inject

class PreferencesInitializer : Initializer<Unit> {

    @set:Inject
    internal lateinit var preferences: Preferences

    override fun create(context: Context) {
        PreferencesEntryPoint.resolve(context).inject(this)
        AuthService.authSpotify = preferences.spotifyToken.decodeTo(AuthSpotifyResponse::class.java)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf()
}