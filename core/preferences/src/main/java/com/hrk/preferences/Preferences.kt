package com.hrk.preferences

import android.content.SharedPreferences
import com.hrk.preferences.delegate.stringPreferences
import javax.inject.Inject

class Preferences @Inject constructor(
    val sharedPreferences: SharedPreferences
) {
    var spotifyToken: String by stringPreferences(
        key = SPOTIFY_AUTH_KEY,
        defaultValue = String.Empty
    )

    companion object {
        private const val SPOTIFY_AUTH_KEY: String = "key_spotify_auth"
    }
}

val String.Companion.Empty
    inline get() = ""

