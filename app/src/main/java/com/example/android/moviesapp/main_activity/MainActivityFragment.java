package com.example.android.moviesapp.main_activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.moviesapp.internet_connection_error_activity.No_internetActivity;
import com.example.android.moviesapp.R;
import com.example.android.moviesapp.adapter.ImageAdapter;
import com.example.android.moviesapp.detail_activity.MovieDetailActivity;
import com.example.android.moviesapp.fectching.FetchMovie;
import com.example.android.moviesapp.movieInfo.Movies;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AdapterView.OnItemClickListener{
    ImageAdapter adapter = null;


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
        adapter = new ImageAdapter(getActivity());
        movies_gridView.setAdapter(adapter);

        movies_gridView.setOnItemClickListener(this);
        return  rootView;
    }

    public void setPreference()
    {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Fetching the movie
            FetchMovie movie = new FetchMovie(adapter);

            // declare a sharedPreference to get the user preference
            SharedPreferences sharedPreferences =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());

            // Store the user preference
            String sort_preference = sharedPreferences.getString(getString(R.string.pref_key),
                    getString(R.string.pref_default));

            movie.execute(sort_preference);

        } else {

            Intent intent_no_connection = new Intent(getActivity(), No_internetActivity.class);
            startActivity(intent_no_connection);
            return;

        }

    }


    @Override
    public void onResume() {
        super.onResume();

        setPreference();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // get the movie that is clicked
        Movies movie = (Movies)adapter.getItem(position);

        // pass the position of the movie to the MovieDetailActivity class
        Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, position);

        // start the activity
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}
