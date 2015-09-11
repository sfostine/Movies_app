package com.example.android.moviesapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Samuel on 15-09-10.
 */
public class ImageAdapter extends BaseAdapter {
    Context context;
    public static ArrayList<String> movie_list = new ArrayList<String>();




    public ImageAdapter(Context cont){
        this.context = cont;

    }

    @Override
    public int getCount() {
        return movie_list.size();
    }

    @Override
    public Object getItem(int position) {
        return movie_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView imageView;

        //if the view is null, inflate the view, then set the view to imageView
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.movies_item, parent, false);

            imageView = (ImageView)view.findViewById(R.id.movie_picture);

        }
        else
            imageView = (ImageView)convertView;


        // using picasso to load images
        Picasso.with(context).load(movie_list.get(position)).into(imageView);


        //return view;
        return imageView;
    }
}
