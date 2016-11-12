package com.example.biro.movieapp;


import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.support.annotation.RequiresApi;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLData;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements MainFragment.Communication{



    private Toolbar toolbar;

    private DetailsFragment detailsFragment;
    private android.app.FragmentTransaction ft;
    private Intent intent;

    public static boolean isTwoPane() {
        return twoPane;
    }

    private static boolean twoPane;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                finish();
                startActivity(getIntent());
                return true;
            }
        });
        DataBaseManger.getInstance(getApplicationContext()).getWritableDatabase();




        if(findViewById(R.id.detailfragmentcontainer)==null)
        {
            twoPane=false;
        }
        else
        {
            twoPane=true;
        }


        if (savedInstanceState == null) {
            MainFragment mainFragment = new MainFragment();
            android.app.FragmentManager manager = getFragmentManager();
            mainFragment.setCommunication(this);
            ft = manager.beginTransaction();
            ft.add(R.id.mainfragcontainer, mainFragment, "Myfragment");
            ft.commit();
            toolbar.setTitle(Shared.getInstance(this).load("catetitle"));

        }

        else
        {
            MainFragment mainFragment = new MainFragment();
            android.app.FragmentManager manager = getFragmentManager();
            ft = manager.beginTransaction();
            mainFragment.setCommunication(this);
            ft.replace(R.id.mainfragcontainer,mainFragment);
            ft.commit();
            toolbar.setTitle(savedInstanceState.getString("CateTitle"));
        }





    }


    @Override
    public void communicate(Movie m) {

            if(twoPane)
            {
                detailsFragment = new DetailsFragment();
                Bundle b = new Bundle();
                b.putParcelable("Movie",m);
                detailsFragment.setArguments(b);
                getFragmentManager().beginTransaction().replace(R.id.detailfragmentcontainer,detailsFragment).commit();
            }
        else
            {
                intent = new Intent(this,DetailsActivity.class);
                intent.putExtra("Movie",m);
                intent.putExtra("Title",m.getMovieName());
                startActivity(intent);

            }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String title = Shared.getInstance(this).load("catetitle");
        outState.putString("CateTitle",title);
    }



}









