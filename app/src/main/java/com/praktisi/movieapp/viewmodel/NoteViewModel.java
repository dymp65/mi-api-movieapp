package com.praktisi.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.praktisi.movieapp.models.Note;
import com.praktisi.movieapp.sqlite.AppDatabase;

import java.util.List;

public class NoteViewModel extends ViewModel {
    MutableLiveData<List<Note>> note = new MutableLiveData<>();
    private AppDatabase appDatabase;

    public NoteViewModel(@NonNull Application application) {
        appDatabase = AppDatabase.getInstance(application);
    }

    public LiveData<List<Note>> getNote() {
        return appDatabase.noteDao().getAllNotes();
    }
//
    public void saveNote(Note note) {
        appDatabase.noteDao().insertNote(note);
    }
//
    public void updateNote(Note note) {
        appDatabase.noteDao().updateNote(note);
    }

    public void deleteNote(Note note) {
        appDatabase.noteDao().deleteNote(note);
    }
}
