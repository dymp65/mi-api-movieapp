package com.praktisi.movieapp.sqlite.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.praktisi.movieapp.models.TvShow;

import java.util.List;

@Dao
public interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTvShow(TvShow tvShow);

    @Delete
    void deleteTvShow(TvShow tvShow);

    @Query("SELECT * FROM tv_show WHERE id = :id")
    TvShow getTvShowById(int id);

    @Query("SELECT * FROM tv_show")
    LiveData<List<TvShow>>  getAllTvShows();
}
