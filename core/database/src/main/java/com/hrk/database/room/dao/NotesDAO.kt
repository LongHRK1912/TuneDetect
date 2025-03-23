package com.hrk.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.hrk.database.room.model.NoteEntity

@Dao
interface NotesDAO {
    @Query("SELECT * FROM note_hrk ORDER BY created_at DESC")
    suspend fun getNoteEntities(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(toteEntity: NoteEntity): Long

    @Delete
    suspend fun deleteNote(toteEntity: NoteEntity): Int

    @Update
    suspend fun updateNote(toteEntity: NoteEntity): Int
}
