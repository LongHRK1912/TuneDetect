package com.hrk.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hrk.database.room.RoomConst.CURRENT_VERSION
import com.hrk.database.room.dao.NotesDAO
import com.hrk.database.room.model.NoteEntity

@Database(
    entities = [
        NoteEntity::class
    ],
    version = CURRENT_VERSION,
    exportSchema = true
)
internal abstract class HRKDatabase : RoomDatabase() {
    abstract fun noteDao(): NotesDAO
}
