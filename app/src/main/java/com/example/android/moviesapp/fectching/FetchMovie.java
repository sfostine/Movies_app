package com.example.android.moviesapp.fectching;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.moviesapp.adapter.ImageAdapter;
import com.example.android.moviesapp.movieInfo.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Samuel on 15-09-10.
 */
public class  FetchMovie extends AsyncTask<String,Void,Movies[]> {
    ImageAdapter imageAdapter;

    // create another constructor with imageview to notify imageView when preference changes
    public FetchMovie(ImageAdapter adapter)
    {
        imageAdapter = adapter;
    }



    public Movies[] getDataFromAPI(String jsonString, String preference) throws JSONException {
        int num;

        final String BASE_URL = "http://image.tmdb.org/t/p/w342/";
        final String RESULTS = "results";
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        // assign the value of num
        num = jsonArray.length();

        //String [] resultsLink = new String[num];

        Movies[] moviesList = new Movies[num];


        for(int i = 0; i < num; i++) {
            int id = jsonArray.getJSONObject(i).getInt("id");
            String name = jsonArray.getJSONObject(i).getString("original_title");
            String date = jsonArray.getJSONObject(i).getString("release_date");
            String link = BASE_URL + jsonArray.getJSONObject(i).getString("poster_path");
            Double rate = jsonArray.getJSONObject(i).getDouble("vote_average");
            String detail = jsonArray.getJSONObject(i).getString("overview");
            moviesList[i] = new Movies(id,name, date, link, rate, detail);
            //resultsLink[i] = link;
        }

        if(preference.equals("rated"))
            SortedByVote(moviesList);


        return moviesList;
    }

    // sort the movie list by average_vote
    public void SortedByVote(Movies [] list)
    {

        for(int i = 0; i < list.length; i++)
        {
            for (int j= 1; j < list.length; j++)
            {
                if(list[j-1].getAverage_count() < list[j].getAverage_count())
                {
                    Movies temp = list[j];
                    list[j] = list[j-1];
                    list[j-1] = temp;
                }
            }
        }
    }


    @Override
    protected Movies[] doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader buffer = null;
        String jSonString;

        try{
            //connect to the URL
            // Note that you need to get your own API
            URL url = new URL("http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=YOUR_KEY");
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // read the inputstream
            InputStream stream = urlConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            // if the stream is null, then add a null string to the jsonstring
            if(stream == null)
            {
                jSonString = null;
            }

            // read the stream using a string buffer
            String line;
            buffer = new BufferedReader(new InputStreamReader(stream));

            while((line = buffer.readLine()) != null )
            {
                stringBuffer.append(line+ "\n");
            }

            // if the string buffer doesn't have any string, that means the stream was empty
            if(stringBuffer.length() == 0)
            {
                jSonString = null;
            }


            //convert the string buffer to string
            jSonString = stringBuffer.toString();

        }
        catch (IOException e)
        {
            Log.v("FetchMovieError", "Error" + e);
            jSonString = null;

        }finally {

            if(urlConnection != null)
                urlConnection.disconnect();
            if(buffer != null)
                try {
                    buffer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }

        try {
            return getDataFromAPI(jSonString, params[0]);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("getDataFromAPI ERROR", "" + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(Movies[] strings) {
        super.onPostExecute(strings);
        if (strings != null) {
            ImageAdapter.movie_list.clear();
            for (Movies s : strings)
                ImageAdapter.movie_list.add(s);
        }
        imageAdapter.notifyDataSetChanged();
        this.cancel(true);

    }
}