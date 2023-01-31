package com.darf.android.uni1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class NoteViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable = CompositeDisposable()

    private val _notes = MutableLiveData<List<Note>>()
    var notes: LiveData<List<Note>> = _notes

    private var noteRepository: NoteRepository =
        NoteRepository(AppDatabase.getDatabase(application).noteDao())

    fun getNotesById(id: String) {
        noteRepository.getNotesById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                if (!it.isNullOrEmpty()) {
                    _notes.postValue(it)
                } else {
                    _notes.postValue(listOf())
                }
            }.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }

    fun updateNote(note: Note) {
        noteRepository.updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ getNotesById(note.id) }, {})
            .let { compositeDisposable.add(it) }
    }

    fun saveNote(note: Note) {
        noteRepository.insertNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ getNotesById(note.id) }, {})
            .let { compositeDisposable.add(it) }
    }

    fun deleteNote(note: Note) {
        noteRepository.deleteNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ getNotesById(note.id) }, {})
            .let { compositeDisposable.add(it) }
    }
}