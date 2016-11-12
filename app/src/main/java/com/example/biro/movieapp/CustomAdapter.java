package com.example.biro.movieapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by biro on 10/5/2016.
 */

public class CustomAdapter extends BaseAdapter {

    ArrayList<Movie> movies;
    Context context;

    public class ViewManger{

        ImageView imageView;
        TextView  movieName;
        TextView  movieDate;

        ViewManger(View v)
        {
            imageView = (ImageView) v.findViewById(R.id.imageView);
            movieName = (TextView) v.findViewById(R.id.movieName);
            movieDate = (TextView) v.findViewById(R.id.movieDate);

        }
    }




    CustomAdapter(Context context,ArrayList<Movie> movies)
    {
        this.movies=movies;
        this.context=context;

    }

    public int getCount() {


        return movies.size();
    }

    @Override
    public Object getItem(int position) {
        return movies.get(position);

    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater l = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewManger holder;
      if(convertView==null)
      {
        convertView =    l.inflate(R.layout.grid_item, parent ,false);
          holder = new ViewManger(convertView);
          convertView.setTag(holder);
      }
        else
      {
        holder = (ViewManger) convertView.getTag();
      }


        Movie temp = movies.get(position);
        Picasso.with(context).load(temp.getImageUrl()).into(holder.imageView);
        String []year = temp.getMovieYear().split("-");
        holder.movieDate.setText(year[0]);
        holder.movieName.setText(temp.getMovieName());



        return convertView;
    }







}
