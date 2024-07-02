package com.praktisi.movieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.app.Application;
import androidx.annotation.NonNull;

import com.praktisi.movieapp.BuildConfig;
import com.praktisi.movieapp.api.ApiService;
import com.praktisi.movieapp.api.MResponse;
import com.praktisi.movieapp.models.Movie;
import com.praktisi.movieapp.sqlite.AppDatabase;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends ViewModel {
    MutableLiveData<MResponse> mresponse = new MutableLiveData<>();
    MutableLiveData<Movie> movie = new MutableLiveData<>();
    MutableLiveData<String> failure = new MutableLiveData<>();
    private String key = BuildConfig.API_KEY;

    private AppDatabase appDatabase;

    public MovieViewModel(@NonNull Application application) {
        appDatabase = AppDatabase.getInstance(application);
    }

    public void fetchMovie(ApiService service, int page){
        service.getPopularMovies(page, key).enqueue(new Callback<MResponse<Movie>>() {
            @Override
            public void onResponse(Call<MResponse<Movie>> call, Response<MResponse<Movie>> response) {
                if (response.isSuccessful() && response.body() != null){
                    mresponse.postValue(response.body());
                } else {
                    failure.postValue("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<MResponse<Movie>> call, Throwable t) {
                failure.postValue(t.getMessage());
            }
        });
    }

    public void setMovieDetail(ApiService service, int id){
        service.getMovie(id, key).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful() && response.body() != null){
                    movie.postValue(response.body());
                } else {
                    failure.postValue("Failed to fetch data");
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                failure.postValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<Movie> getMovie() {
        return movie;
    }

    public MutableLiveData<MResponse> getMResponse() {
        return mresponse;
    }

    public MutableLiveData<String> getFailure() {
        return failure;
    }

    public LiveData<List<Movie>> getFavoriteMovie() {
        return appDatabase.movieDao().getAllMovies();
    }

    public Movie getFavoriteMovieById(int id) {
        return appDatabase.movieDao().getMovieById(id);
    }

    public void insertFavoriteMovie(Movie movie) {
        new Thread (() -> appDatabase.movieDao().insertMovie(movie)).start();
    }

    public void deleteFavoriteMovie(Movie movie) {
        new Thread(() -> appDatabase.movieDao().deleteMovie(movie)).start();
    }
}
