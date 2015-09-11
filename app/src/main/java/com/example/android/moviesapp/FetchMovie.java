package com.example.android.moviesapp;

import android.os.AsyncTask;
import android.util.Log;

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
public class FetchMovie extends AsyncTask<Void,Void,String[]> {

    public String[] getDataFromAPI(String jsonString) throws JSONException {
        int num;

        final String BASE_URL = "http://image.tmdb.org/t/p/w342/";
        final String RESULTS = "results";
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONArray(RESULTS);

        // assign the value of num
        num = jsonArray.length();
        String [] resultsLink = new String[num];

        for(int i = 0; i < num; i++) {
            resultsLink[i] = BASE_URL + jsonArray.getJSONObject(i).getString("poster_path");
            String l = resultsLink[i];

        }
        return resultsLink;
    }


    @Override
    protected String[] doInBackground(Void... params) {
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
            return getDataFromAPI(jSonString);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.v("getDataFromAPI ERROR", "" + e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        super.onPostExecute(strings);
        if (strings != null) {
            ImageAdapter.movie_list.clear();
            for (String s : strings)
                ImageAdapter.movie_list.add(s);
        }

    }

}
