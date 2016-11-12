package com.example.biro.movieapp;

import android.app.Activity;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by biro on 10/4/2016.
 */

public class Movie implements Parcelable{

    private String movieName;
    private String movieYear;
    private String overview;
    private String lang;
    private String imageUrl;
    private int id;
    private double ratting;

//    private ArrayList<String> reviews = new ArrayList<String>();

/*


    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }
    */
    public Movie()
    {

    }


    private Movie(Parcel in) {
        movieName = in.readString();
        movieYear = in.readString();
        overview = in.readString();
        imageUrl = in.readString();
        id = in.readInt();
        ratting = in.readDouble();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMovieName() {
        return movieName;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public String getOverview() {
        return overview;
    }

    public String getLang() {
        return lang;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public double getRatting() {
        return ratting;
    }



    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public void setimageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public void setRatting(double ratting) {
        this.ratting = ratting;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(movieName);
        dest.writeString(movieYear);
        dest.writeString(overview);
        dest.writeString(imageUrl);
        dest.writeInt(id);
        dest.writeDouble(ratting);




    }
}
