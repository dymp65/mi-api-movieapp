package com.praktisi.movieapp;

import com.praktisi.movieapp.sqlite.AppDatabase;

public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.createDatabase(this);
    }
}

