package com.mwsmith.popularmoviesstage1;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matthew Smith on 26/01/2018.
 */

public final class Movie implements Parcelable {

    public static final String PARCEL_NAME = "MOVIE";
    private static final String INPUT_DATE_FORMAT = "yyyy-MM-dd";
    private static final String DISPLAY_DATE_FORMAT = "dd MMM yyyy";

    private final int mId;
    private final String mTitle;
    private final Date mReleaseDate;
    private final String mPosterPath;
    private final double mVoteAverage;
    private final String mPlotSynopsis;

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

    public Movie(int id, String title, String releaseDate, String posterPath, double voteAverage, String plotSynopsis) throws ParseException {
        mId = id;
        mTitle = title;
        Locale locale = Locale.getDefault();
        DateFormat df = new SimpleDateFormat(INPUT_DATE_FORMAT, locale);
        mReleaseDate = df.parse(releaseDate);
        mPosterPath = posterPath;
        mVoteAverage = voteAverage;
        mPlotSynopsis = plotSynopsis;
    }

    private Movie(Parcel parcel) {
        mId = parcel.readInt();
        mTitle = parcel.readString();
        mReleaseDate = new Date(parcel.readLong());
        mPosterPath = parcel.readString();
        mVoteAverage = parcel.readDouble();
        mPlotSynopsis = parcel.readString();
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getReleaseDate() {
        Locale locale = Locale.getDefault();
        DateFormat df = new SimpleDateFormat(DISPLAY_DATE_FORMAT, locale);
        return df.format(mReleaseDate);
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mTitle);
        parcel.writeLong(mReleaseDate.getTime());
        parcel.writeString(mPosterPath);
        parcel.writeDouble(mVoteAverage);
        parcel.writeString(mPlotSynopsis);
    }
}
