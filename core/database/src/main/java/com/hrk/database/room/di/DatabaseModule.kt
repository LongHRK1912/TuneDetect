package com.intelin.core.database.room.di

import android.content.Context
import androidx.room.Room
import com.hrk.database.room.HRKDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
    ): HRKDatabase = Room.databaseBuilder(
        context,
        HRKDatabase::class.java,
        "note_hrk_studio",
    ).addMigrations().build()

}
