package com.example.android.moviesapp.detail_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.moviesapp.R;
import com.example.android.moviesapp.adapter.ImageAdapter;
import com.example.android.moviesapp.movieInfo.Movies;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment {
    TextView movie_title, date_textview, duration_textview, rate_textview, overview;
    ImageView detail_image;

    public MovieDetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the view
        View rootView= inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // get the views
        movie_title = (TextView)rootView.findViewById(R.id.movie_title_view);
        date_textview = (TextView)rootView.findViewById(R.id.date_textview);
        rate_textview = (TextView)rootView.findViewById(R.id.rating_textview);
        overview = (TextView)rootView.findViewById(R.id.overview_textView);
        detail_image = (ImageView)rootView.findViewById(R.id.detail_imageview);

        //get the intent
        Intent intent = getActivity().getIntent();
        int position = intent.getIntExtra(Intent.EXTRA_TEXT, 0);

        // retreive the movie
        Movies movie = ImageAdapter.movie_list.get(position);

        //set the value of the views
        movie_title.setText(movie.getTitle());
        date_textview.setText(movie.getDate());
        rate_textview.setText(movie.getAverage_count() + "/10");
        overview.setText(movie.getOverView());
        Picasso.with(getActivity()).load(movie.getLink()).into(detail_image);

        return rootView;

    }
}
