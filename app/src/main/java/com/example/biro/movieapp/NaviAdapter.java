package com.example.biro.movieapp;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by biro on 10/17/2016.
 */

public class NaviAdapter extends BaseAdapter {


    Context context;
    ViewManger manger;
    ArrayList<NaviModel> model = new ArrayList<NaviModel>();
    NaviModel [] x = new NaviModel[4];

    public NaviAdapter(Context context) {
        this.context= context;

        String []array= context.getResources().getStringArray(R.array.categ);
        int []array2={R.drawable.nowplaying,
                R.drawable.mostpopular,
                R.drawable.toprated
                ,R.drawable.favoruites};

        for(int i = 0 ; i<x.length ;i++)
        {
            x[i] = new NaviModel();
            x[i].setImage(array2[i]);
            x[i].setText(array[i]);

        }

    }


    @Override
    public int getCount() {
        return x.length;
    }

    @Override
    public Object getItem(int position) {
        return x[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) // creating row for first time
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.navi_item, parent, false);
             manger = new ViewManger(convertView);
             convertView.setTag(manger);

        }
        else //recyling
        {
              manger = (ViewManger) convertView.getTag(); // no new operation

        }

        manger.cate.setText(x[position].getText());
        manger.icon.setImageResource(x[position].getImage());

        return convertView;
    }

    public class ViewManger {

        ImageView icon;
        TextView cate;

        ViewManger(View v) {
            icon = (ImageView) v.findViewById(R.id.cateImgae);
            cate = (TextView) v.findViewById(R.id.cateText);

        }
    }
}

