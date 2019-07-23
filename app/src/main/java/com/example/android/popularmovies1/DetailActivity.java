package com.example.android.popularmovies1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.popularmovies1.model.Movie;
import com.example.android.popularmovies1.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private String mMovieName;
    private ImageView mMoviePoster;
    private TextView mMovieDate;
    private TextView mMovieRating;
    private TextView mMovieOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mMovieDate = (TextView) findViewById(R.id.movie_date);
        mMoviePoster = (ImageView) findViewById(R.id.movie_poster);
        mMovieRating = (TextView) findViewById(R.id.movie_rating);
        mMovieOverview = (TextView) findViewById(R.id.movie_overview);

        //get the movie object data. code learnt from resource 5 in the reviewer note
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movie")) {
                Movie movie = (Movie) intentThatStartedThisActivity.getParcelableExtra("movie");
                mMovieDate.setText(movie.getReleaseDate());
                String image = NetworkUtils.buildImageUrl(movie.getImage());
                Picasso.with(this).load(image).placeholder(R.drawable.placeholder_movie).fit().into(mMoviePoster);
                mMovieRating.setText(movie.getRating());
                mMovieOverview.setText(movie.getOverview());
                mMovieName = movie.getTitle();
                setTitle(mMovieName);
            }
        }

    }
}
