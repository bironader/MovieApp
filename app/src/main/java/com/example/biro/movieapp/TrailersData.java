package com.example.biro.movieapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.ExpandableListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by biro on 10/27/2016.
 */

public class TrailersData extends AsyncTask<String, String, ArrayList<Trailers>> {

    private ArrayList<Trailers> trailersData;
    Activity activity;
    private static final String parentArr = "youtube";
    private static final String NAME = "name";
    private static final String SOURCE = "source";
    private HashMap<String, ArrayList<?>> parentChild = new HashMap<String, ArrayList<?>>();
    private String parent[] = {"Reviews", "Trailers"};
    private ExpandableListView exp;


    public HashMap<String, ArrayList<?>> getParentChild() {
        return parentChild;
    }


    public String[] getParent() {
        return parent;
    }


    public TrailersData(Activity activity) {
        this.activity = activity;
        this.trailersData = new ArrayList<Trailers>();
    }


    public ArrayList<Trailers> getTrailersData() {
        return trailersData;
    }

    @Override
    protected ArrayList<Trailers> doInBackground(String... params) {


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
            JSONArray parentArray = parentObject.getJSONArray(parentArr);
            Trailers temp[] = new Trailers[parentArray.length()];
            if (parentArray.length() == 0) {
                Trailers t = new Trailers();
                t.setName("No trailers to show");
                trailersData.add(t);
                return trailersData;
            } else {
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    temp[i] = new Trailers();
                    temp[i].setName(finalObject.getString(NAME));
                    temp[i].setSource("https://www.youtube.com/watch?v=" + finalObject.getString(SOURCE));
                    trailersData.add(temp[i]);
                }
                return trailersData;

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailersData;

    }
}
