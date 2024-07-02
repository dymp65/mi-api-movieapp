package com.praktisi.movieapp.sqlite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.praktisi.movieapp.models.Movie;
import com.praktisi.movieapp.models.Note;
import com.praktisi.movieapp.models.TvShow;
import com.praktisi.movieapp.sqlite.dao.MovieDao;
import com.praktisi.movieapp.sqlite.dao.NoteDao;
import com.praktisi.movieapp.sqlite.dao.TvShowDao;

@Database(
        entities = {
                Movie.class,
                TvShow.class,
                Note.class
        },
        version = 1,
        exportSchema = false
)

public abstract class AppDatabase extends RoomDatabase {
    public abstract MovieDao movieDao();
    public abstract TvShowDao tvShowDao();

    public abstract NoteDao noteDao();

    private static AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                                    AppDatabase.class, "movie_database")
                            .build();
                }
            }
        }

        return instance;
    }

    public static void createDatabase(Context context) {
        instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "my_movie_app"
                )
                .allowMainThreadQueries()
                .build();
    }

}
