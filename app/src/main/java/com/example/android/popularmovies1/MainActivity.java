package com.example.android.popularmovies1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.popularmovies1.model.Movie;
import com.example.android.popularmovies1.utilities.JSONUtils;
import com.example.android.popularmovies1.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnClickHandler {
    private MoviesAdapter mMoviesAdapter;
    private RecyclerView mMoviesList;
    private TextView mErrorMessage;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMoviesList = (RecyclerView) findViewById(R.id.rv_movies);
        mErrorMessage = (TextView) findViewById(R.id.error_message);
        //GridLayoutManager code from : Resource2 in reviewer note
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMoviesList.setLayoutManager(layoutManager);
        mMoviesList.setHasFixedSize(true);
        if (NetworkUtils.isOnline(this)) {
            sortMovies("popular");
        } else {
            showErrorMessage();
        }
    }


    public void sortMovies(String sortOrder) {
        mMoviesAdapter = new MoviesAdapter(this);
        mMoviesList.setAdapter(mMoviesAdapter);
        URL urlPopular = NetworkUtils.buildURL(sortOrder);
        new UrlQueryTask().execute(urlPopular);

    }

    //show error message and hide the recyclerview
    private void showErrorMessage() {
        mMoviesList.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * This method is overridden by our MainActivity class in order to handle RecyclerView item
     * clicks.
     *
     * @param movie The movie that was clicked
     */
    @Override
    public void onClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        // pass the movie object to the detail activity. Code learnt from Resource 5 in the reviewer note
        intentToStartDetailActivity.putExtra("movie", movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.sort_popular) {
            item.setChecked(true);
            menu.findItem(R.id.sort_rating).setChecked(false);
            sortMovies("popular");
            setTitle("most popular");

            return true;
        }

        if (id == R.id.sort_rating) {
            item.setChecked(true);
            menu.findItem(R.id.sort_popular).setChecked(false);
            sortMovies("top_rated");
            setTitle("highest rated");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class UrlQueryTask extends AsyncTask<URL, Void, Movie[]> {
        @Override
        protected Movie[] doInBackground(URL... params) {
            URL url = params[0];
            String results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(url);
                Movie[] movies_list;
                movies_list = JSONUtils.parseMovieJson(results);
                return movies_list;

            } catch (IOException e) {
                e.printStackTrace();
                return null;

            }
        }

        @Override
        protected void onPostExecute(Movie[] movies_list) {
            if (movies_list != null) {
                mMoviesAdapter.setMovieData(movies_list);
            }
        }

    }
}




