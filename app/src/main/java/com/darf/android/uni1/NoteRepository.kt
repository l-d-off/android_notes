package com.darf.android.uni1

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Maybe

class NoteRepository(private val noteDao: NoteDao) {
    fun getNotes(): Maybe<List<Note>> {
        return noteDao.getAll()
    }

    fun getNotesById(id: String): Maybe<List<Note>> {
        return noteDao.getById(id)
    }

    fun insertNote(note: Note): Completable {
        return noteDao.insert(note)
    }

    fun updateNote(note: Note): Completable {
        return noteDao.update(note)
    }

    fun deleteNote(note: Note): Completable {
        return noteDao.delete(note)
    }
}