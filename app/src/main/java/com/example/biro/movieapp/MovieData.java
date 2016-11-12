package com.example.biro.movieapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;




public class MovieData extends AsyncTask <String, String, ArrayList<Movie>> {


    private ArrayList<Movie> movies;
    private Activity activity;
    GridView movieGrid;



    private static final String results = "results";
    private static final String title = "title";
    private static final String date = "release_date";
    private static final String language = "original_language";
    private static final String description = "overview";
    private static final String image = "https://image.tmdb.org/t/p/w500/";
    private static final String poster = "poster_path";
    private static final String vote = "vote_average";
    public  static String checker ="home" ;







    public MovieData(Activity activity) {


        movies = new ArrayList<Movie>();
        this.activity = activity;

    }







    //get movies data from api and pasring the jason
    protected ArrayList<Movie> doInBackground(String... params) {
        HttpURLConnection connection = null;
        String result = "";
        BufferedReader reader = null;


        try {

            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            StringBuffer buffer = new StringBuffer();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            while ((result = reader.readLine()) != null)
                buffer.append(result); // final json string



            String jason = buffer.toString();  //parsing jason

            JSONObject parentObject = new JSONObject(jason);     //parentobject
            JSONArray parentArray = parentObject.getJSONArray(results);  //parent array
            Movie[] tempMovie = new Movie[parentArray.length()];
            for (int i = 0; i < parentArray.length(); i++) {

                JSONObject finalObjects = parentArray.getJSONObject(i);
                tempMovie[i] = new Movie();
                String movieName = finalObjects.getString(title);
                String movieYear = finalObjects.getString(date);
                String lang = finalObjects.getString(language);
                String overview = finalObjects.getString(description);
                String imageUrl = image + finalObjects.get(poster);
                int id = finalObjects.getInt("id");
                double ratting = finalObjects.getDouble(vote);
                tempMovie[i].setOverview(overview);
                tempMovie[i].setMovieName(movieName);
                tempMovie[i].setLang(lang);
                tempMovie[i].setMovieYear(movieYear);
                tempMovie[i].setimageUrl(imageUrl);
                tempMovie[i].setRatting(ratting);
                tempMovie[i].setId(id);
                movies.add(tempMovie[i]);

            }

            return movies;


        } catch (MalformedURLException e) {
            e.printStackTrace();
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


    protected void onPostExecute(ArrayList<Movie> m) {
        super.onPostExecute(m);
        movieGrid = (GridView) activity.findViewById(R.id.movieGrid);
        if(isNetworkConnected())
        {

            CustomAdapter adapter = new CustomAdapter(activity,m);
            movieGrid.setAdapter(adapter);

        }
        else {
            Toast.makeText(activity, "No internet connection", Toast.LENGTH_SHORT).show();
            movieGrid.setAdapter(null);
        }

    }


    public ArrayList<Movie> getMovies() {
        return movies;
    }



    public boolean isNetworkConnected ()
    {
        ConnectivityManager networkManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkState = networkManager.getActiveNetworkInfo();

        boolean connected = (networkState != null && networkState.isConnected());

        return connected;
    }

}

