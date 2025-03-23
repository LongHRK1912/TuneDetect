package com.hrk.database.room.di

import com.hrk.database.room.HRKDatabase
import com.hrk.database.room.dao.NotesDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesNoteDao(
        database: HRKDatabase,
    ): NotesDAO = database.noteDao()
}
