package com.example.biro.movieapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.biro.movieapp.Contract.*;

/**
 * Created by biro on 10/21/2016.
 */

public class DataBaseManger extends SQLiteOpenHelper {


    private final static String DATABASE_NAME = "TheMoviesZone.db";
    private final static int DATABASE_VERSION = 1;

    private static DataBaseManger instance = null;


    private DataBaseManger(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    public static DataBaseManger getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseManger(context);
        }
        return instance;
    }

    public void onCreate(SQLiteDatabase db) {


        db.execSQL("CREATE TABLE " + MoviesTable.TABLE_NAME + "( " + MoviesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + MoviesTable.COLUMN_MOVIE_NAME + " VARCHAR(255) NOT NULL, "
                + MoviesTable.COLUMN_MOVIE_NUM + " INTEGER NOT NULL,"
                + MoviesTable.COLUMN_MOVIE_YEAR + " VARCHAR(255) NOT NULL, "
                + MoviesTable.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, "
                + MoviesTable.COLUMN_POSTER + " TEXT NOT NULL, "
                + MoviesTable.COLUMN_MOVIE_RATE + " REAL NOT NULL" +
                ");"

        );
        /*

        db.execSQL("CREATE TABLE " + ReviewsTable.TABLE_NAME + "(" + ReviewsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ReviewsTable.COLUMN_AUTHOR + " VARCHAR(255) NOT NULL,"
                + ReviewsTable.COLUMN_REVIEWS + " TEXT NOT NULL,"
                + ReviewsTable.F_COLUMN_MOVIE_ID + " INTEGER,"
                + " FOREIGN KEY (" + ReviewsTable.F_COLUMN_MOVIE_ID + ") REFERENCES " + MoviesTable.TABLE_NAME + "(" + MoviesTable._ID + ")" +

                ");"


        );


        db.execSQL("CREATE TABLE " + TrailersTable.TABLE_NAME + "(" + TrailersTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TrailersTable.COLUMN_SOURCE + " TEXT NOT NULL, "
                + TrailersTable.COLUMN_TITLE + " TEXT NOT NULL,"
                + TrailersTable.F_COLUMN_MOVIE_ID + " INTEGER,"
                + " FOREIGN KEY (" + TrailersTable.F_COLUMN_MOVIE_ID + ") REFERENCES " + TrailersTable.TABLE_NAME + "(" + MoviesTable._ID + ")" +

                ");"


        );

*/
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addMovie(Movie m) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MoviesTable.COLUMN_MOVIE_NAME, m.getMovieName());
        values.put(MoviesTable.COLUMN_MOVIE_NUM, m.getId());
        values.put(MoviesTable.COLUMN_MOVIE_YEAR, m.getMovieYear());
        values.put(MoviesTable.COLUMN_MOVIE_OVERVIEW, m.getOverview());
        values.put(MoviesTable.COLUMN_POSTER, m.getImageUrl());
        values.put(MoviesTable.COLUMN_MOVIE_RATE, m.getRatting());

        long rowInserted = db.insert(MoviesTable.TABLE_NAME, null, values);
        db.close();



    }

/*
    public void addReviews(int id, ArrayList<Reviews> r) {
        int _id = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        db = this.getReadableDatabase();
        String[] columns = {MoviesTable._ID};
        String selection = MoviesTable.COLUMN_MOVIE_NUM + " = '" + id + "'";
        Cursor c = db.query(MoviesTable.TABLE_NAME, columns, selection, null, null, null, null);

        while (c.moveToNext()) {
            int index = c.getColumnIndex(MoviesTable._ID);
            _id = c.getInt(index);
        }


        c.close();
        ContentValues values = new ContentValues();

        for (int i = 0; i < r.size(); i++) {
            values.put(ReviewsTable.COLUMN_AUTHOR, r.get(i).getName());
            values.put(ReviewsTable.COLUMN_REVIEWS, r.get(i).getReview());
            values.put(ReviewsTable.F_COLUMN_MOVIE_ID, _id);
            long rowInserted = db.insert(ReviewsTable.TABLE_NAME, null, values);


        }
        db.close();

    }
    */
/*
    public void addRTrailers(int id, ArrayList<Trailers> t) {
        int _id = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String[] columns = {MoviesTable._ID};
        String selection = MoviesTable.COLUMN_MOVIE_NUM + " = '" + id + "'";
        Cursor c = db.query(MoviesTable.TABLE_NAME, columns, selection, null, null, null, null);
        ContentValues values = new ContentValues();


        while (c.moveToNext()) {
            int index = c.getColumnIndex(MoviesTable._ID);
            _id = c.getInt(index);
        }
        for (int i = 0; i < t.size(); i++) {
            values.put(TrailersTable.COLUMN_TITLE, t.get(i).getName());
            values.put(TrailersTable.COLUMN_SOURCE, t.get(i).getSource());
            values.put(TrailersTable.F_COLUMN_MOVIE_ID, _id);
            long rowInserted = db.insert(TrailersTable.TABLE_NAME, null, values);

        }
        db.close();
    }
*/
    public void deleteMovie(Movie m) {
        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String where = MoviesTable.COLUMN_MOVIE_NUM + "=?";
        String whereArgs[] = {"" + m.getId()};
        db.delete(MoviesTable.TABLE_NAME, where, whereArgs);

        db.close();


    }

    public ArrayList<Movie> getAllMovies() {
        ArrayList<Movie> m = new ArrayList<Movie>();
        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String[] column = {MoviesTable.COLUMN_MOVIE_NAME, MoviesTable.COLUMN_MOVIE_NUM, MoviesTable.COLUMN_MOVIE_OVERVIEW, MoviesTable.COLUMN_POSTER, MoviesTable.COLUMN_MOVIE_RATE, MoviesTable.COLUMN_MOVIE_YEAR};
        Cursor c = db.query(MoviesTable.TABLE_NAME, column, null, null, null, null, null);
        int name = c.getColumnIndex(MoviesTable.COLUMN_MOVIE_NAME);
        int num = c.getColumnIndex(MoviesTable.COLUMN_MOVIE_NUM);
        int overview = c.getColumnIndex(MoviesTable.COLUMN_MOVIE_OVERVIEW);
        int rate = c.getColumnIndex(MoviesTable.COLUMN_MOVIE_RATE);
        int poster = c.getColumnIndex(MoviesTable.COLUMN_POSTER);
        int year = c.getColumnIndex(MoviesTable.COLUMN_MOVIE_YEAR);
        Movie[] temp = new Movie[c.getCount()];

                for(int i = 0 ; i<c.getCount();i++) {
                    while (c.moveToNext()) {
                        temp[i] = new Movie();
                        temp[i].setMovieName(c.getString(name));
                        temp[i].setimageUrl(c.getString(poster));
                        temp[i].setMovieYear(c.getString(year));
                        temp[i].setRatting(c.getDouble(rate));
                        temp[i].setId(c.getInt(num));
                        temp[i].setOverview(c.getString(overview));
                        m.add(temp[i]);
                    }
                }



        c.close();
        db.close();

        return m;
    }


    public boolean exists(Movie m) {
        SQLiteDatabase db = this.getReadableDatabase();
        db = this.getWritableDatabase();
        String columns[] = {MoviesTable.COLUMN_MOVIE_NAME, MoviesTable.COLUMN_MOVIE_NUM, MoviesTable.COLUMN_MOVIE_YEAR, MoviesTable.COLUMN_MOVIE_OVERVIEW, MoviesTable.COLUMN_POSTER, MoviesTable.COLUMN_MOVIE_RATE};
        String selection = MoviesTable.COLUMN_MOVIE_NUM + " = '" + m.getId() + "'";
        Cursor c = db.query(MoviesTable.TABLE_NAME, columns, selection, null, null, null, null);
        if (c.getCount() > 0) {
            c.close();
            db.close();

            return true;
        } else {
            c.close();
            db.close();

            return false;
        }


    }


}



