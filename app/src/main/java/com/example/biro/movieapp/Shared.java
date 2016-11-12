package com.example.biro.movieapp;

import android.app.Activity;
import android.content.Context;

/**
 * Created by biro on 10/21/2016.
 */

public class Shared {

    private Activity activity;
    private android.content.SharedPreferences sharedPreferences;
    private android.content.SharedPreferences.Editor editor;
    private static Shared instance = null;
    public static final String DEFAULT = "NO DATA ON PREF";

    private  Shared(Activity activity) {
        this.activity = activity;
        this.sharedPreferences = activity.getSharedPreferences("MovieApp", Context.MODE_PRIVATE);
    }


    public static Shared getInstance(Activity activity) {
        if (instance == null) {
            instance = new Shared(activity);
        }
        return instance;
    }

    public void save(String tag,String data) {

        editor = sharedPreferences.edit();
        editor.putString(tag,data);
        editor.commit();


    }
    public String load(String tag)
    {
        return sharedPreferences.getString(tag,DEFAULT);
    }
}
