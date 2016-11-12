package com.example.biro.movieapp;

import android.provider.Telephony;

/**
 * Created by biro on 10/28/2016.
 */

public class Contract {


    public static final class MoviesTable implements Telephony.BaseMmsColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_MOVIE_NAME = "name";
        public static final String COLUMN_POSTER = "poster";
        public static final String COLUMN_MOVIE_NUM = "number";
        public static final String COLUMN_MOVIE_YEAR = "year";
        public static final String COLUMN_MOVIE_RATE = "rate";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
    }
/*
    public static final class ReviewsTable implements Telephony.BaseMmsColumns {

        public static final String TABLE_NAME = "reviews";
        public static final String F_COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_AUTHOR = "author";
        public static final String COLUMN_REVIEWS = "reviews";
    }
    */
/*
    public static  final class TrailersTable implements Telephony.BaseMmsColumns{

        public static final String TABLE_NAME="trailers";
        public static final String F_COLUMN_MOVIE_ID="movie_id";
        public static final String COLUMN_SOURCE="source";
        public static final String COLUMN_TITLE="title";
    }
    public static final class Favorites implements Telephony.BaseMmsColumns{

        public static final String TABLE_NAME="favorites";
        public static final String F_COLUMN_REVIEWS_ID = "reviews_id";
        public static final String F_COLUMN_TRAILERS_ID =  "trailers_id";
        public static final String COLUMN_MOVIE_NAME ="name";
        public static final String COLUMN_POSTER="poster";
        public static final String COLUMN_MOVIE_NUM = "number";
        public static final String COLUMN_MOVIE_YEAR ="year";
        public static final String COLUMN_MOVIE_RATE ="rate";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";    }
}
*/
}