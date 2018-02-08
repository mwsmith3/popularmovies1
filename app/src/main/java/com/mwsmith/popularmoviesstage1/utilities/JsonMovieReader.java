package com.mwsmith.popularmoviesstage1.utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mwsmith.popularmoviesstage1.Movie;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Matthew Smith on 23/01/2018.
 */


public class JsonMovieReader {

    public static List<Movie> getMovieList(String jsonMovieData) {

        try {
            List<Movie> movieList = new ArrayList<>();

            JSONObject jsonMoviesObject = new JSONObject(jsonMovieData);
            JSONArray jsonMoviesArray = jsonMoviesObject.getJSONArray("results");

            for (int i = 0; i < jsonMoviesArray.length(); i++) {

                JSONObject jsonMovie = jsonMoviesArray.getJSONObject(i);

                int id = jsonMovie.getInt("id");
                String title = jsonMovie.getString("title");
                String releaseDateString = jsonMovie.getString("release_date");
                String posterPath = jsonMovie.getString("poster_path");
                double voteAverage = jsonMovie.getDouble("vote_average");
                String plotSynopsis = jsonMovie.getString("overview");

                try {
                    Movie movie = new Movie(id, title, releaseDateString, posterPath, voteAverage, plotSynopsis);
                    movieList.add(movie);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return movieList;

        } catch (JSONException e) {
            return Collections.emptyList();
        }
    }
}
