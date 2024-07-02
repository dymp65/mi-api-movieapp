package com.praktisi.movieapp.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.praktisi.movieapp.R;
import com.praktisi.movieapp.adapter.MovieAdapter;
import com.praktisi.movieapp.api.ApiClient;
import com.praktisi.movieapp.databinding.FragmentMovieBinding;
import com.praktisi.movieapp.models.Movie;
import com.praktisi.movieapp.ui.DetailActivity;
import com.praktisi.movieapp.utils.PaginationScrollListener;
import com.praktisi.movieapp.viewmodel.MovieViewModel;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

import java.util.ArrayList;
import java.util.List;

public class MovieFragment extends Fragment implements MovieAdapter.OnClickListener{

    MovieViewModel movieViewModel;
    FragmentMovieBinding binding;
    LinearLayoutManager layoutManager;
    MovieAdapter adapter;
    Skeleton skeleton;
    private static final int PAGE_START = 1;
    private int currentPage = PAGE_START;
    private int pageSize;
    private int totalPages = 0;
    private boolean isLoading;
    private final String TAG = MovieFragment.class.getSimpleName();
    private ArrayList<Movie> movies = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        skeleton = container.findViewById(R.id.skeleton_layout);
        init();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieViewModel.getMResponse().observe(getViewLifecycleOwner(), movie -> {
            totalPages = movie.total_pages;
            currentPage = movie.page;
            pageSize = movie.results.size();

            if (currentPage == PAGE_START) {
                movies = (ArrayList<Movie>) movie.results;
                adapter = new MovieAdapter(getContext(), movies);
                adapter.setOnClickListener(this);
                binding.rvMovie.setAdapter(adapter);
            } else {
                movies.remove(movies.size() - 1);
                int scrollPosition = movies.size();
                adapter.notifyItemRemoved(scrollPosition);
                movies.addAll(movie.results);
                adapter.notifyDataSetChanged();
                isLoading = false;
            }

//            adapter.notifyDataSetChanged();
            Log.i(TAG, "getUsersList: " + movies);
        });

        movieViewModel.getFailure().observe(getViewLifecycleOwner(), s -> {
            if (currentPage == PAGE_START) {
                skeleton.showOriginal();
                Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
            }

            if (movies != null && movies.size() > 0) {
                movies.remove(movies.size() - 1);
                adapter.notifyItemRemoved(movies.size());
                currentPage--;
                isLoading = false;
            }
        });
    }

    private void init() {
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        if (movieViewModel != null) {
             skeleton = SkeletonLayoutUtils.applySkeleton(binding.rvMovie, R.layout.item_movie, 10);
             skeleton.showSkeleton();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    movieViewModel.fetchMovie(ApiClient.getApiService(getContext()), PAGE_START);
                }
            }, 800);
        }

        layoutManager = new LinearLayoutManager(getContext());
        binding.rvMovie.setLayoutManager(layoutManager);

        loadMore();
    }

    private void loadMore() {
        binding.rvMovie.addOnScrollListener(new PaginationScrollListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                currentPage ++;
                isLoading = true;

                //add handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        movieViewModel.fetchMovie(ApiClient.getApiService(getContext()), currentPage);

                        Movie movie = new Movie();
                        movie.setTitle("progress");
                        movies.add(movies.size(), movie);
                        adapter.notifyItemInserted(movies.size());
                    }
                }, 1000);

                Log.d("Page", "loadMoreItems: " + currentPage + " totalPage" + totalPages);
            }

            @Override
            public int getTotalPageCount() {
                return totalPages;
            }

            @Override
            public boolean isLastPage() {
                return currentPage == totalPages ? true : false;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public int getPageSize() {
                return pageSize;
            }
        });
    }

    @Override
    public void onClick(int position, List<Movie> movies) {
        Movie movie = movies.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(DetailActivity.TYPE, DetailActivity.TYPE_MOVIE);
        bundle.putInt(DetailActivity.ID, movie.getId());
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}