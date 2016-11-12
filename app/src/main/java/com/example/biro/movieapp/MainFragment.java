package com.example.biro.movieapp;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by biro on 10/9/2016.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class MainFragment extends Fragment {

    private DrawerLayout drawerLayout;
    private ListView left_drawer;
    private ActionBarDrawerToggle drawerToggle;
    private GridView Grid;
    private DBAsync homefav;
    private Toolbar toolbar;
    private NaviAdapter myAdapter;
    private  final static String apiKey="api_key=6d54cce40ee7b14971ed74495fc72c79&language=en-US&page=1";
    private final static String nowPlaying = "https://api.themoviedb.org/3/movie/now_playing?"+apiKey;
    private final static String popular = "https://api.themoviedb.org/3/movie/popular?"+apiKey;
    private final static String top_rated = "https://api.themoviedb.org/3/movie/top_rated?"+apiKey;
    private MovieData home;
    private MovieData nowPlayingData;
    private MovieData mostPopularData;
    private MovieData topRatedData;
    private DBAsync favouritesData;
    private Communication communication;



    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment, container, false);
        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer);
        left_drawer = (ListView) getActivity().findViewById(R.id.left);
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        left_drawer = (ListView) getActivity().findViewById(R.id.left);
        drawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerToggle.setDrawerIndicatorEnabled(true);
        drawerToggle.setHomeAsUpIndicator(R.drawable.icon);
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);
        myAdapter = new NaviAdapter(getActivity());

        left_drawer.setAdapter(myAdapter);
        left_drawer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                if (position == 0) {

                    drawerLayout.closeDrawers();
                    toolbar.setTitle("Now Playing");
                    nowPlayingData = new MovieData(getActivity());
                    nowPlayingData.execute(nowPlaying);
                    Shared.getInstance(getActivity()).save("url", nowPlaying);
                    Shared.getInstance(getActivity()).save("catetitle","Now Playing");
                    MovieData.checker = "now playing";

                } else if (position == 1) {

                    drawerLayout.closeDrawers();
                    toolbar.setTitle("Most Popular");
                    mostPopularData = new MovieData(getActivity());
                    mostPopularData.execute(popular);
                    Shared.getInstance(getActivity()).save("url", popular);
                    Shared.getInstance(getActivity()).save("catetitle","Most Popular");
                    MovieData.checker = "most popular";


                } else if (position == 2) {

                    drawerLayout.closeDrawers();
                    toolbar.setTitle("Top Rated");
                    topRatedData = new MovieData(getActivity());
                    topRatedData.execute(top_rated);
                    Shared.getInstance(getActivity()).save("url",top_rated);
                    Shared.getInstance(getActivity()).save("catetitle","Top Rated");
                    MovieData.checker = "top rated";

                } else if (position == 3) {

                    drawerLayout.closeDrawers();
                    toolbar.setTitle("Favourites");

                    favouritesData = new DBAsync(getActivity());
                    favouritesData.execute();
                    Shared.getInstance(getActivity()).save("url","fav");
                    Shared.getInstance(getActivity()).save("catetitle","Favourites");
                    MovieData.checker = "favourites";
                }

            }
        });


        if(Shared.getInstance(getActivity()).load("url").equals(Shared.DEFAULT))
        {
            home = new MovieData(getActivity());
            home.execute(nowPlaying);
        }
        else if(Shared.getInstance(getActivity()).load("url").equals("fav"))
        {
            homefav = new DBAsync(getActivity());
            homefav.execute();
            MovieData.checker="homefav";
        }
        else
        {
            home = new MovieData(getActivity());
            home.execute(Shared.getInstance(getActivity()).load("url"));
            MovieData.checker="home";
        }









        Grid = (GridView) v.findViewById(R.id.movieGrid);
        Grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (MovieData.checker.equals("home")) {

                    Movie temp = new Movie();
                    temp = home.getMovies().get(position);
                    communication.communicate(temp);


                } else if (MovieData.checker.equals("now playing")) {
                    Movie temp = new Movie();
                    temp = nowPlayingData.getMovies().get( position);
                    communication.communicate(temp);



                } else if (MovieData.checker.equals("most popular")) {
                    Movie temp = new Movie();
                   temp= mostPopularData.getMovies().get(position);
                    communication.communicate(temp);



                } else if (MovieData.checker.equals("top rated")) {
                    Movie temp = new Movie();
                    temp = topRatedData.getMovies().get(position);
                    communication.communicate(temp);


                } else if (MovieData.checker.equals("favourites")) {
                    Movie temp = new Movie();
                    temp = favouritesData.getMoviesFromDb().get(position);
                    communication.communicate(temp);


                }
                else if(MovieData.checker.equals("homefav"))
                {
                    Movie temp = new Movie();
                    temp = homefav.getMoviesFromDb().get(position);
                    communication.communicate(temp);

                }

            }

        });


        return v;
    }












    interface  Communication{
        public void communicate (Movie m);
    }

    public  void setCommunication(Communication communication) {
        this.communication = communication;
    }
}















