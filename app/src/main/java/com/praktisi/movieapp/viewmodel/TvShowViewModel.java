package com.praktisi.movieapp.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.praktisi.movieapp.BuildConfig;
import com.praktisi.movieapp.api.ApiService;
import com.praktisi.movieapp.api.MResponse;
import com.praktisi.movieapp.models.TvShow;
import com.praktisi.movieapp.sqlite.AppDatabase;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class TvShowViewModel extends ViewModel {
    MutableLiveData<MResponse> mResponse = new MutableLiveData<>();
    MutableLiveData<TvShow> tvShow = new MutableLiveData<>();
    MutableLiveData<String> failure = new MutableLiveData<>();
    private String key = BuildConfig.API_KEY;

    private AppDatabase appDatabase;

    public TvShowViewModel(@NonNull Application application) {
        appDatabase = AppDatabase.getInstance(application);
    }
    public void fetchTvShow(ApiService service, int page) {
        service.getPopularTvShows(page, key).enqueue(new Callback<MResponse<TvShow>>() {
            @Override
            public void onResponse(Call<MResponse<TvShow>> call, Response<MResponse<TvShow>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    mResponse.postValue(response.body());
                } else {
                    mResponse.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<MResponse<TvShow>> call, Throwable t) {
                failure.postValue(t.getMessage());
            }
        });
    }

    public void setTvShowDetail(ApiService service, int id) {
        service.getTvShow(id, key).enqueue(new Callback<TvShow>() {
            @Override
            public void onResponse(Call<TvShow> call, Response<TvShow> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tvShow.setValue(response.body());
                } else {
                    tvShow.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<TvShow> call, Throwable t) {
                failure.postValue(t.getMessage());
            }
        });
    }

    public MutableLiveData<MResponse> getMResponse() {
        return mResponse;
    }

    public MutableLiveData<String> getFailure() {
        return failure;
    }

    public MutableLiveData<TvShow> getTvShow() {
        return tvShow;
    }

    public LiveData<List<TvShow>> getFavoriteTvShow() {
        return appDatabase.tvShowDao().getAllTvShows();
    }

    public void insertFavoriteTvShow(TvShow tvShow) {
        appDatabase.tvShowDao().insertTvShow(tvShow);
    }

    public void deleteFavoriteTvShow(TvShow tvShow) {
        appDatabase.tvShowDao().deleteTvShow(tvShow);
    }

    public TvShow getFavoriteTvShowById(int id) {
        return appDatabase.tvShowDao().getTvShowById(id);
    }
}
