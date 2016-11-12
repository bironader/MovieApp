package com.example.biro.movieapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by biro on 11/7/2016.
 */

public class DBAsync extends AsyncTask <Void, Void, ArrayList<Movie>> {

    Activity c;
    private  ArrayList<Movie> moviesFromDb;
    private GridView movieGrid;

    public ArrayList<Movie> getMoviesFromDb() {
        return moviesFromDb;
    }

    public DBAsync(Activity c) {
        this.c = c;
    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {
        this.moviesFromDb = DataBaseManger.getInstance(c).getAllMovies();
        return moviesFromDb;
    }

    @Override
    protected void onPostExecute(ArrayList<Movie> movies) {
        movieGrid = (GridView) c.findViewById(R.id.movieGrid);
        if(movies.size()==0) {

            movieGrid.setAdapter(null);
            Toast.makeText(c, "No favourite movies", Toast.LENGTH_SHORT).show();
        }
        else
        {
            CustomAdapter ad = new CustomAdapter(c,movies);
            movieGrid.setAdapter(ad);
        }
    }
}
