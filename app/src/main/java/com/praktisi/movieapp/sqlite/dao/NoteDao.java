package com.praktisi.movieapp.sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.praktisi.movieapp.models.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM note WHERE id = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM note")
    LiveData<List<Note>> getAllNotes();

    @Update
    void updateNote(Note note);
}
