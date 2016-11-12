package com.example.biro.movieapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by biro on 10/25/2016.
 */

public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Context c;
    private String[] header;
    private ViewManger holder;


    private HashMap<String, ArrayList<?>> parentChild;
    private int icons[] = {R.drawable.letter, R.drawable.trailer};

    // private int arrows [] ={R}
    public ExpandableListAdapter(Context c, HashMap<String, ArrayList<?>> parentChild, String[] header) {
        this.c = c;
        this.header = header;
        this.parentChild = parentChild;

    }

    public class ViewManger {

        //View line;
        TextView expParent;
        TextView expchild2;
        TextView expChild;
        ImageView icon;
        ImageView arrows;
        TextView trailerText;
        Button play;

        ViewManger(View v) {

            expParent = (TextView) v.findViewById(R.id.parentExp);
            expChild = (TextView) v.findViewById(R.id.childExp);
            icon = (ImageView) v.findViewById(R.id.icon);
            arrows = (ImageView) v.findViewById(R.id.rightIcon);
            expchild2 = (TextView) v.findViewById(R.id.expchild2);
            trailerText = (TextView) v.findViewById(R.id.trailerText);
            play = (Button) v.findViewById(R.id.playButton);
            //line = v.findViewById(R.id.view);
        }
    }


    @Override
    public int getGroupCount() {
        return header.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.parentChild.get(header[groupPosition]).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.parentChild.get(header[groupPosition]).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewManger holder;
        LayoutInflater l = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        String tempParent = (String) getGroup(groupPosition);
        if (convertView == null) {
            convertView = l.inflate(R.layout.exp_parent, parent, false);
            holder = new ExpandableListAdapter.ViewManger(convertView);
            convertView.setTag(holder);

        } else {
            holder = (ViewManger) convertView.getTag();
        }
        if (isExpanded) {
            holder.arrows.setImageResource(R.drawable.downarrow);
        } else {
            holder.arrows.setImageResource(R.drawable.rightarrow);
        }
        holder.expParent.setTypeface(null, Typeface.BOLD);
        holder.expParent.setText(tempParent);

        holder.icon.setImageResource(icons[groupPosition]);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {

        //int type = this.getChildType(groupPosition,childPosition);
        LayoutInflater l = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        switch (groupPosition) {
            case 0:
                 Reviews temp = (Reviews) getChild(groupPosition, childPosition);
                convertView = l.inflate(R.layout.exp_reviews, parent, false);
                holder = new ViewManger(convertView);
                holder.expchild2.setText("Author: " + temp.getName());
                holder.expChild.setText(temp.getReview());
                break;
            case 1:
                final Trailers temp2 = (Trailers) getChild(groupPosition, childPosition);
                convertView = l.inflate(R.layout.exp_trailers, parent, false);

                holder = new ViewManger(convertView);
                holder.trailerText.setText(temp2.getName());
                if(temp2.getSource()==null)
                {
                    Toast.makeText(c,"No trailers to play",Toast.LENGTH_LONG).show();
                    holder.play.setEnabled(false);
                }
                else
                {
                    holder.play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(temp2.getSource()));

                            c.startActivity(Intent.createChooser(intent, "Choose App"));

                        }

                    });
                }

                break;


        }
        return convertView;
    }


    @Override
    public int getChildType(int groupPosition, int childPosition) {
        return super.getChildType(groupPosition, childPosition);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
        if (parentChild.get(header[groupPosition]).size()==0)
         Toast.makeText(c,"Check your internet connection",Toast.LENGTH_LONG).show();


    }
}
