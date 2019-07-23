package com.example.android.popularmovies1.model;

import android.os.Parcel;
import android.os.Parcelable;

// code inspired from Resource4 in the reviewer note
public class Movie implements Parcelable {

    private String poster_path;
    private String id;
    private String title;
    private String release_date;
    private String rating;
    private String overview;

    public Movie() {

    }

    public Movie(String id,String title, String poster_path, String release_date, String rating, String overview) {
        this.id = id;
        this.title=title;
        this.poster_path = poster_path;
        this.release_date=release_date;
        this.rating=rating;
        this.overview=overview;

    }

    //reconstructing user object from parcel
    public Movie(Parcel in) {
        id = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        rating = in.readString();
        overview = in.readString();
        title=in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // object serialization
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(rating);
        dest.writeString(overview);
        dest.writeString(title);
    }

    //creating new objects, individually or as arrays
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }

    };

    public String getTitle() {
        return title ;
    }
    public String getImage() {
        return poster_path;
    }
    public String getReleaseDate() {
        return release_date;
    }
    public String getRating() {
        return rating;
    }
    public String getOverview() {
        return overview;
    }
}
