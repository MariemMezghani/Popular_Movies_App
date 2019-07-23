package com.example.android.popularmovies1.utilities;

import com.example.android.popularmovies1.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JSONUtils {
    public static Movie[] parseMovieJson(String json) {
        try {
            //convert json string to json object
            JSONObject moviesJsonObject = new JSONObject(json);
            //getting JSON Array node (Resource1)
            JSONArray movies = moviesJsonObject.getJSONArray("results");
            Movie[] movies_list = new Movie[movies.length()];
            //looping through all movies
            for (int i = 0; i< movies.length(); i++){
                JSONObject c = movies.getJSONObject(i);
                String id = c.getString("id");
                String title = c.getString("original_title");
                String image = c.getString("poster_path");
                String release_date=c.getString("release_date");
                String rating =c.getString("vote_average");
                String overview=c.getString("overview");
                Movie movie = new Movie(id,title, image,release_date, rating, overview);
                movies_list[i]= movie;

            }
            return movies_list;

        } catch (JSONException e) {
            e.printStackTrace();
            return null;


        }
    }
}
