package com.praktisi.movieapp.ui;

import android.graphics.drawable.Drawable;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.praktisi.movieapp.BuildConfig;
import com.praktisi.movieapp.R;
import com.praktisi.movieapp.api.ApiClient;
import com.praktisi.movieapp.databinding.ActivityDetailBinding;
import com.praktisi.movieapp.helpers.ViewHelper;
import com.praktisi.movieapp.models.Movie;
import com.praktisi.movieapp.models.TvShow;
import com.praktisi.movieapp.viewmodel.MovieViewModel;
import com.praktisi.movieapp.viewmodel.TvShowViewModel;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.google.android.material.appbar.MaterialToolbar;

public class DetailActivity extends AppCompatActivity {

    public static final String KEY_MOVIE = "data";
    public static final String TYPE = "type";
    public static final String TYPE_MOVIE = "movie";
    public static final String TYPE_TV = "tv";
    public static final String ID = "id";
    MovieViewModel movieViewModel;
    TvShowViewModel tvShowViewModel;
    ActivityDetailBinding binding;
    Skeleton skeleton, skeletonBackdrop;
    private String type;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        skeleton = binding.skeletonLayoutDetail;
        skeletonBackdrop = binding.skeletonBackdrop;
        setContentView(binding.getRoot());

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        ViewHelper.setStatusBarColor(this, R.color.black_20, false);
        init();
        setToolbar();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        tvShowViewModel = new ViewModelProvider(this).get(TvShowViewModel.class);

        if (type.equals(TYPE_MOVIE)) {
            movieViewModel.setMovieDetail(ApiClient.getApiService(this), id);
            movieViewModel.getMovie().observe(this, movieDetailObserver);
        } else if (type.equals(TYPE_TV)) {
            tvShowViewModel.setTvShowDetail(ApiClient.getApiService(this), id);
            tvShowViewModel.getTvShow().observe(this, tvShowDetailObserver);
        }
    }

    private void init() {
        skeleton = SkeletonLayoutUtils.createSkeleton(binding.main);
        skeleton.showSkeleton();
        type = getIntent().getStringExtra(TYPE);
        id = getIntent().getIntExtra(ID, 0);
    }

    private void setToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar_detail);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
    private void setTvShowData(TvShow tvShow) {
        binding.tvTitleDetail.setText(tvShow.getName());
        binding.tvOverviewDetail.setText(tvShow.getOverview());
        binding.tvRateMovieDetail.setText(String.valueOf((float) tvShow.getVoteAverage()/2));
        binding.tvReleaseDateDetail.setText("Release: " + tvShow.getFirstAirDate());
        binding.rateMovieDetail.setRating((float) (tvShow.getVoteAverage()/2));
        Glide.with(this)
                .load(BuildConfig.URL_IMG_ITEM + tvShow.getPosterPath())
                .into(binding.ivMovieDetail);

        skeletonBackdrop = SkeletonLayoutUtils.createSkeleton(binding.ivBackdrop);
        skeletonBackdrop.showSkeleton();
        Glide.with(this)
                .load(BuildConfig.URL_IMG + tvShow.getBackdropPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        skeletonBackdrop.showOriginal();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        skeletonBackdrop.showOriginal();
                        return false;
                    }
                })
                .into(binding.ivBackdrop);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvOverviewDetail.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

        addFavorite(TYPE_TV, null, tvShow);
        TvShow check = tvShowViewModel.getFavoriteTvShowById(tvShow.getId());
        if (check != null) {
            setFabColorFavorite();
        } else {
            setFabColorUnFavorite();
        }
    }
    private void setMovieData(Movie movie) {
        binding.tvTitleDetail.setText(movie.getTitle());
        binding.tvOverviewDetail.setText(movie.getOverview());
        binding.tvRateMovieDetail.setText(String.valueOf((float) movie.getVoteAverage()/2));
        binding.tvReleaseDateDetail.setText("Release: " + movie.getReleaseDate());
        binding.rateMovieDetail.setRating((float) (movie.getVoteAverage()/2));
        Glide.with(this)
                .load(BuildConfig.URL_IMG_ITEM + movie.getPosterPath())
                .into(binding.ivMovieDetail);

        skeletonBackdrop = SkeletonLayoutUtils.createSkeleton(binding.ivBackdrop);
        skeletonBackdrop.showSkeleton();
        Glide.with(this)
                .load(BuildConfig.URL_IMG + movie.getBackdropPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        skeletonBackdrop.showOriginal();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        skeletonBackdrop.showOriginal();
                        return false;
                    }
                })
                .into(binding.ivBackdrop);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            binding.tvOverviewDetail.setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
        }

//        setCollapseToolbar(movie.getTitle());
        addFavorite(TYPE_MOVIE, movie, null);
        Movie check = movieViewModel.getFavoriteMovieById(movie.getId());
        if (check != null) {
            setFabColorFavorite();
        } else {
            setFabColorUnFavorite();
        }
    }

    private final Observer<Movie> movieDetailObserver = movie -> {
        if (movie != null) {
            skeleton.showOriginal();
            setMovieData(movie);
        }
    };

    private final Observer<TvShow> tvShowDetailObserver = tvShow -> {
        if (tvShow != null) {
            skeleton.showOriginal();
            setTvShowData(tvShow);
        }
    };

    private void setCollapseToolbar(String title) {
        binding.collapsingToolbar.setTitle(title);
        binding.collapsingToolbar.setExpandedTitleColor(getResources().getColor(android.R.color.white));
    }

    private void addFavorite(String type, Movie movie, TvShow tvShow) {
        binding.fabFavorite.setOnClickListener(v -> {
            if (type.equals(TYPE_MOVIE)) {
                Movie check = movieViewModel.getFavoriteMovieById(movie.getId());
                if (check == null) {
                    movieViewModel.insertFavoriteMovie(movie);
                    Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    setFabColorFavorite();
                } else {
                    movieViewModel.deleteFavoriteMovie(movie);
                    Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    setFabColorUnFavorite();
                }
            } else if (type.equals(TYPE_TV)) {
                TvShow check = tvShowViewModel.getFavoriteTvShowById(tvShow.getId());
                if (check == null) {
                    tvShowViewModel.insertFavoriteTvShow(tvShow);
                    Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();
                    setFabColorFavorite();
                } else {
                    tvShowViewModel.deleteFavoriteTvShow(tvShow);
                    Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show();
                    setFabColorUnFavorite();
                }
            }
        });
    }

    private void setFabColorFavorite() {
        binding.fabFavorite.setColorFilter(
                ContextCompat.getColor(this, android.R.color.holo_orange_light)
        );
    }

    private void setFabColorUnFavorite() {
        binding.fabFavorite.setColorFilter(
                ContextCompat.getColor(this, android.R.color.white)
        );
    }
}