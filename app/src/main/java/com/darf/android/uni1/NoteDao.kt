package com.darf.android.uni1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

@Dao
interface NoteDao {
    @Query("select * from note")
    fun getAll(): Maybe<List<Note>>

    @Query("select * from note where id = :id")
    fun getById(id: String): Maybe<List<Note>>

    @Insert
    fun insert(note: Note): Completable

    @Update
    fun update(note: Note): Completable

    @Delete
    fun delete(note: Note): Completable
}