package com.mwsmith.popularmoviesstage1.utilities;

import android.net.Uri;

import com.mwsmith.popularmoviesstage1.Movie;
import com.mwsmith.popularmoviesstage1.SortOrder;
import com.mwsmith.popularmoviesstage1.utilities.Data.Keys;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Matthew Smith on 23/01/2018.
 */

public class NetworkUtils {

    private static final String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static final String API_QUERY_PARAMETER = "api_key";
    private static final String IMAGE_BASE_URL = "http://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w342//";

    public static URL buildMovieDbUrl(SortOrder sortOrder) {

        String sortOrderPath = sortOrder.getUrlString();

        Uri builtUri = Uri.parse(BASE_URL)
                .buildUpon()
                .appendPath(sortOrderPath)
                .appendQueryParameter(API_QUERY_PARAMETER, Keys.API_KEY)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    // Taken from Android Developer Nanodegree Program, "ud851-Sunshine\S04.03-Solution-AddMapAndSharing"
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getImagePath(Movie movie) {
        return IMAGE_BASE_URL + IMAGE_SIZE + movie.getPosterPath();
    }
}
