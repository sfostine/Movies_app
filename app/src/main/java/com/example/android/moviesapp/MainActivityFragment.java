package com.example.android.moviesapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment{


    public MainActivityFragment() {

    }

    // create the view for the fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View rootView =inflater.inflate(R.layout.fragment_main, container, false);

        // extract the Gridview
        GridView movies_gridView = (GridView)rootView.findViewById(R.id.grid_movies_list);
        movies_gridView.setAdapter(new ImageAdapter(getActivity()));

        return  rootView;
    }

    // when the fragment is starting, fetch the API
    @Override
    public void onStart() {
        super.onStart();
        FetchMovie fetchMovie = new FetchMovie();
        fetchMovie.execute();
    }
}
