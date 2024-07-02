package com.praktisi.movieapp.api;

import com.praktisi.movieapp.models.Movie;
import com.praktisi.movieapp.models.TvShow;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("movie/popular")
    Call<MResponse<Movie>> getPopularMovies(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}")
    Call<Movie> getMovie(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );

    @GET("tv/popular")
    Call<MResponse<TvShow>> getPopularTvShows(
            @Query("page") int page,
            @Query("api_key") String apiKey
    );

    @GET("tv/{id}")
    Call<TvShow> getTvShow(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
}
