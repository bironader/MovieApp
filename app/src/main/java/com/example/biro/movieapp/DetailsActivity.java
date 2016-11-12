package com.example.biro.movieapp;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Intent intent;
    private String movieTitle;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        intent = getIntent();
        movieTitle = intent.getStringExtra("Title");
        toolbar.setTitle(movieTitle);



    }

}
