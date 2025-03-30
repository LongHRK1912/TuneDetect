package com.hrk.preferences.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.hrk.apps.hrkdev.core.AppConstants
import com.hrk.preferences.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object PreferencesModule {

    @Provides
    @Singleton
    fun providePreferences(@ApplicationContext context: Context): Preferences {
        val sharedPreferences =
            context.getSharedPreferences(AppConstants.preferencesKey, MODE_PRIVATE)
        return Preferences(sharedPreferences)
    }
}
