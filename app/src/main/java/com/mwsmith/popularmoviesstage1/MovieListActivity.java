package com.mwsmith.popularmoviesstage1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mwsmith.popularmoviesstage1.utilities.JsonMovieReader;
import com.mwsmith.popularmoviesstage1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private final SortOrder SORT_ORDER_DEFAULT = SortOrder.POPULARITY;

    private GridView mMovieGridView;
    private TextView mErrorMessageTextView;
    private ImageAdapter mImageAdapter;
    private SortOrder mSortOrder;
    private boolean fetchMovieTaskIsRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        setSortOrder(SORT_ORDER_DEFAULT);
        setupMovieGridView();
        loadMovieData();
    }

    private void setupMovieGridView() {
        mErrorMessageTextView = findViewById(R.id.movie_list_error_message);
        mMovieGridView = findViewById(R.id.movie_grid_view);
        mImageAdapter = new ImageAdapter(this);
        mMovieGridView.setAdapter(mImageAdapter);
        mMovieGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Movie clickedMovie = (Movie) mImageAdapter.getItem(i);

                Context context = MovieListActivity.this;
                Class destinationClass = MovieDetailsActivity.class;
                Intent intentToStartMovieDetailActivity = new Intent(context, destinationClass);

                intentToStartMovieDetailActivity.putExtra(Movie.PARCEL_NAME, clickedMovie);
                startActivity(intentToStartMovieDetailActivity);
            }
        });
    }

    private void setSortOrder(SortOrder sortOrder) {
        mSortOrder = sortOrder;
    }

    private void loadMovieData() {
        if (!fetchMovieTaskIsRunning) {
            fetchMovieTaskIsRunning = true;
            new FetchMovieDataTask().execute();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_order_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        SortOrder chosenSortOrder = SortOrder.getSortOrderFromMenuOption(id);

        if (chosenSortOrder != mSortOrder) {
            mSortOrder = chosenSortOrder;
            loadMovieData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("StaticFieldLeak")
    private class FetchMovieDataTask extends AsyncTask<Void, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Void... voids) {

            List<Movie> movieList;
            URL url = NetworkUtils.buildMovieDbUrl(mSortOrder);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(url);
                movieList = JsonMovieReader.getMovieList(jsonMovieResponse);
                return movieList;
            } catch (IOException e) {
                return Collections.emptyList();
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            fetchMovieTaskIsRunning = false;

            if (movieList.size() > 0) {
                mImageAdapter.setMovieList(movieList);
                showMovieList();
            } else {
                showErrorMessage();
            }
        }
    }

    private void showErrorMessage() {
        mMovieGridView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    private void showMovieList() {
        mMovieGridView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    private class ImageAdapter extends BaseAdapter {

        private final Context mContext;
        private List<Movie> mMovieList = Collections.emptyList();

        public ImageAdapter(Context c) {
            mContext = c;
        }

        @Override
        public Object getItem(int i) {
            return mMovieList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return mMovieList.get(i).getId();
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ImageView imageView;

            if (view == null) {
                imageView = new ImageView(mContext);
                imageView.setAdjustViewBounds(true);
            } else {
                imageView = (ImageView) view;
            }

            Movie movie = (Movie) getItem(i);
            String imagePath = NetworkUtils.getImagePath(movie);

            Picasso.with(mContext).load(imagePath).into(imageView);
            return imageView;
        }

        @Override
        public int getCount() {
            return mMovieList.size();
        }

        private void setMovieList(List<Movie> movieList) {
            mMovieList = movieList;
            notifyDataSetChanged();
        }
    }
}
