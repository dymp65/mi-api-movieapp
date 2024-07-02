package com.praktisi.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.praktisi.movieapp.helpers.ViewHelper;
import com.praktisi.movieapp.ui.note.NoteActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewHelper.setStatusBarColor(this, R.color.black_20, true);
        setToolbar();
        setupNavigationBar();
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.toolbar_menu_note){
            //intent to note activity
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
            return true;
        }

        return  super.onOptionsItemSelected(item);
    }

    private void setupNavigationBar() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fr_container);
        if (navHostFragment == null) {
            return;
        } else {
            // Set up the bottom navigation view with the navigation controller
            BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
            NavigationUI.setupWithNavController(bottomNavigationView,
                    navHostFragment.getNavController());
        }
    }

    private void setToolbar() {
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

}