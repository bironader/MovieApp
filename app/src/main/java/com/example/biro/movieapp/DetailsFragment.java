package com.example.biro.movieapp;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;


import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import java.util.logging.LogRecord;


public class DetailsFragment extends Fragment {
    private TextView title;
    private ImageView image;
    private TextView year;
    private TextView overview;
    private TextView vote;
    private Button favBut;
    private ExpandableListView exp;
    private ViewGroup header;
    private static boolean isFav;
    Movie movie;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if(savedInstanceState==null)
        {
         isFav=false;
        }
        else
        {
            isFav=savedInstanceState.getBoolean("isfav");
        }
    }

    @Nullable
    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v;

        v = inflater.inflate(R.layout.fragment_details, container, false);

        if(MainActivity.isTwoPane()) {
            movie = (Movie) getArguments().getParcelable("Movie");
        }
        else
        {

            Intent intent = getActivity().getIntent();
            movie = (Movie) intent.getParcelableExtra("Movie");
        }


        final ReviewsData reviewsData = new ReviewsData(getActivity());
        reviewsData.execute("https://api.themoviedb.org/3/movie/" + movie.getId() + "/reviews?api_key=6d54cce40ee7b14971ed74495fc72c79&language=en-US&page=1");
        new TrailersData(getActivity()) {

            @Override
            protected void onPostExecute(final ArrayList<Trailers> trailers) {
                super.onPostExecute(trailers);


                exp = (ExpandableListView) activity.findViewById(R.id.Exp);
                getParentChild().put(getParent()[0], reviewsData.getMoviesReview());
                getParentChild().put(getParent()[1], trailers);

                ExpandableListAdapter myAdapter = new ExpandableListAdapter(activity, getParentChild(), getParent());
                header = (ViewGroup) activity.getLayoutInflater().inflate(R.layout.details_header, exp, false);
                exp.addHeaderView(header, null, false);
                exp.setAdapter(myAdapter);
                title = (TextView) header.findViewById(R.id.tilteText);
                overview = (TextView) header.findViewById(R.id.overviewText);
                vote = (TextView) header.findViewById(R.id.voteText);
                image = (ImageView) header.findViewById(R.id.movieImage);
                year = (TextView) header.findViewById(R.id.yearText);
                favBut = (Button) header.findViewById(R.id.makeAssFav);


                Picasso.with(getActivity()).load(movie.getImageUrl()).into(image);
                title.setText(movie.getMovieName());
                year.setText(movie.getMovieYear());
                vote.setText(Double.toString(movie.getRatting()) + "/10");
                overview.setText("’’" + movie.getOverview() + "’’");
                if (DataBaseManger.getInstance(activity).exists(movie)) {
                    favBut.setText("Un Favourite");
                    isFav = true;
                }
                favBut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isFav) {
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    DataBaseManger.getInstance(activity).deleteMovie(movie);
                                    isFav = false;
                                }
                            };
                            Thread t = new Thread(r);
                            t.start();
                            Toast.makeText(getActivity(),movie.getMovieName()+" has removed from favourites",Toast.LENGTH_LONG).show();
                            favBut.setText("Mark as a favourite");


                        } else {
                            Runnable r = new Runnable() {
                                @Override
                                public void run() {
                                    DataBaseManger.getInstance(activity).addMovie(movie);
                                    isFav = true;
                                }
                            };
                            Thread t = new Thread(r);
                            Toast.makeText(getActivity(),movie.getMovieName()+" has added to favourites",Toast.LENGTH_LONG).show();
                            t.start();
                            favBut.setText("Un Favourite");

                        }

                    }
                });
            }

        }.execute("https://api.themoviedb.org/3/movie/" + movie.getId() + "/trailers?api_key=6d54cce40ee7b14971ed74495fc72c79&language=en-US&page=1");


        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("isfav",isFav);
    }
}
