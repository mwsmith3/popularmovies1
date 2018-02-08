package com.mwsmith.popularmoviesstage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwsmith.popularmoviesstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {
    private TextView mTitle, mReleaseDate, mVoteAverage, mOverview;
    private ImageView mMoviePoster;
    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        mTitle = findViewById(R.id.movie_title_tv);
        mReleaseDate = findViewById(R.id.release_date_tv);
        mVoteAverage = findViewById(R.id.vote_average_tv);
        mOverview = findViewById(R.id.movie_overview_tv);
        mMoviePoster = findViewById(R.id.movie_poster_iv);

        getMovie();
        if (mMovie != null) {
            setMovieData();
        }
    }

    private void getMovie() {
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra(Movie.PARCEL_NAME)) {
            mMovie = intentThatStartedThisActivity.getParcelableExtra(Movie.PARCEL_NAME);

        }
    }

    private void setMovieData() {
        String voteAverage = String.valueOf(mMovie.getVoteAverage());

        mTitle.setText(mMovie.getTitle());
        mReleaseDate.setText(mMovie.getReleaseDate());
        mVoteAverage.setText(voteAverage);
        mOverview.setText(mMovie.getPlotSynopsis());

        String imagePath = NetworkUtils.getImagePath(mMovie);
        Picasso.with(this).load(imagePath).into(mMoviePoster);
    }
}
