package com.praktisi.movieapp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.praktisi.movieapp.BuildConfig;
import com.praktisi.movieapp.R;
import com.praktisi.movieapp.models.TvShow;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;

import java.util.ArrayList;
import java.util.List;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class TvShowAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private final String TAG = TvShowAdapter.class.getSimpleName();
    private Context context;
    private View view;
    private ArrayList<TvShow> arrayList;
    private static final int TYPE_PROGRESS = 0;
    private static final int TYPE_DATA = 1;
    private OnClickListener onClickListener;
    public interface OnClickListener {
        void onClick(int position, List<TvShow> tvShows);
    }

    public TvShowAdapter(Context context, ArrayList<TvShow> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == TYPE_DATA) {
            view = inflater.inflate(R.layout.item_movie, parent, false);
            return new TvShowHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_loading, parent, false);
            return new ProgressHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try {
            if (holder != null && holder instanceof TvShowHolder && arrayList.size() > 0) {
                TvShowHolder tvShowHolder = (TvShowHolder) holder;
                TvShow tvShow = arrayList.get(position);
                tvShowHolder.tvTitle.setText(tvShow.getName());
                tvShowHolder.tvTitle.setMaxLines(1);
                tvShowHolder.tvReleaseDate.setText("Release: " + tvShow.getFirstAirDate());
                tvShowHolder.tvOverview.setText(tvShow.getOverview());
                tvShowHolder.tvOverview.setMaxLines(2);
                tvShowHolder.ratingBar.setRating((float) tvShow.getVoteAverage() / 2);

                tvShowHolder.skeleton = SkeletonLayoutUtils.createSkeleton(tvShowHolder.cardView);
                Glide.with(context)
                        .load(BuildConfig.URL_IMG_ITEM + tvShow.getPosterPath())
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                tvShowHolder.skeleton.showOriginal();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                tvShowHolder.skeleton.showOriginal();
                                return false;
                            }
                        })
                        .into(tvShowHolder.ivPoster);
            }
        } catch (Exception e) {
            Log.e(TAG, "onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return arrayList == null ? 0 : arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (!arrayList.get(position).getName().equals("progress")){
            return TYPE_DATA;
        } else {
            return TYPE_PROGRESS;
        }
    }

    private class TvShowHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvReleaseDate, tvOverview;
        private ImageView ivPoster;
        private MaterialRatingBar ratingBar;
        private Skeleton skeleton;
        private CardView cardView;
        public TvShowHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvReleaseDate = view.findViewById(R.id.tv_release_date);
            tvOverview = view.findViewById(R.id.tv_overview);
            ivPoster = view.findViewById(R.id.iv_movie);
            ratingBar = view.findViewById(R.id.rate_movie);
            skeleton = view.findViewById(R.id.skeleton_item_movie);
            cardView = view.findViewById(R.id.card_movie);

            if (onClickListener != null) {
                view.setOnClickListener(v -> {
                    onClickListener.onClick(getAdapterPosition(), arrayList);
                });
            }
        }
    }

    private class ProgressHolder extends RecyclerView.ViewHolder {
        public ProgressHolder(View view) {
            super(view);
        }
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
}
