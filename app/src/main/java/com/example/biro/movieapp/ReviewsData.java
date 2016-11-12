package com.example.biro.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by biro on 10/21/2016.
 */


public class ReviewsData extends AsyncTask<String, String, ArrayList<Reviews>> {


    private static final String RESULTS = "results";
    private static final String CONTENT = "content";
    private static final String AUTHOR = "author";
    Activity activity;

    private ArrayList<Reviews> moviesReview;





    public ArrayList<Reviews> getMoviesReview() {
        return moviesReview;
    }


    public ReviewsData(Activity activity) {
        this.activity = activity;
        this.moviesReview = new ArrayList<Reviews>();


    }


    @Override
    protected ArrayList<Reviews> doInBackground(String... params) {
        HttpURLConnection connection = null;
        String result = "";
        BufferedReader reader = null;
        StringBuffer buffer = new StringBuffer();

        try {

            URL url = new URL(params[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((result = reader.readLine()) != null)
                buffer.append(result);

            String jsn = buffer.toString();  //parsing jason
            JSONObject parentObj = new JSONObject(jsn);
            JSONArray parentArray = parentObj.getJSONArray(RESULTS);
            Reviews[] reviews = new Reviews[parentArray.length()];

            if (parentArray.length() == 0) {
                Reviews DEFAULT = new Reviews();
                DEFAULT.setReview("No reviews to show");
                DEFAULT.setName("N/A");
                moviesReview.add(DEFAULT);
                return moviesReview;

            } else {


                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    reviews[i] = new Reviews();
                    reviews[i].setReview(finalObject.getString(CONTENT));
                    reviews[i].setName(finalObject.getString(AUTHOR));
                    moviesReview.add(reviews[i]);

                }

            }
            return moviesReview;


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;

    }


    @Override
    protected void onPostExecute(ArrayList<Reviews> reviews) {
        super.onPostExecute(reviews);
    }
}