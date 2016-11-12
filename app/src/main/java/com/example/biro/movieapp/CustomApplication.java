package com.example.biro.movieapp;

import android.database.sqlite.SQLiteOpenHelper;

import com.clough.android.androiddbviewer.ADBVApplication;

/**
 * Created by biro on 11/1/2016.
 */

public class CustomApplication extends ADBVApplication {
    @Override
    public SQLiteOpenHelper getDataBase() {
        return DataBaseManger.getInstance(getApplicationContext());
    }



}
