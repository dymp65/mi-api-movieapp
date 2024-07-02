package com.praktisi.movieapp.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.praktisi.movieapp.R;
import com.praktisi.movieapp.ui.favorite.MovieFavoriteFragment;
import com.praktisi.movieapp.ui.favorite.TvShowFavoriteFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;
public class FavoriteFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FragmentPagerItems pages = new FragmentPagerItems(view.getContext());
        pages.add(FragmentPagerItem.of("Movie", MovieFavoriteFragment.class));
        pages.add(FragmentPagerItem.of("Tv Show", TvShowFavoriteFragment.class));

        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), pages);
        // Set the pager with an adapter
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        SmartTabLayout viewPagerTab = view.findViewById(R.id.view_pager_tab);
        viewPagerTab.setViewPager(viewPager);
    }
}