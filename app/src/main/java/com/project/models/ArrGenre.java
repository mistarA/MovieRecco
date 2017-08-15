package com.project.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

/**
 * Created by anandmishra on 23/11/16.
 */

@Generated ("org.jsonschema2pojo")
public class ArrGenre implements Parcelable {

    @SerializedName ("genres")
    @Expose
    private List<Genre> genres = new ArrayList<Genre>();

    /**
     * @return The genres
     */
    public List<Genre> getGenres() {
        return genres;
    }

    /**
     * @param genres The genres
     */
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.genres);
    }

    public ArrGenre() {
    }

    protected ArrGenre(Parcel in) {
        this.genres = in.createTypedArrayList(Genre.CREATOR);
    }

    public static final Parcelable.Creator<ArrGenre> CREATOR = new Parcelable.Creator<ArrGenre>() {
        @Override
        public ArrGenre createFromParcel(Parcel source) {
            return new ArrGenre(source);
        }

        @Override
        public ArrGenre[] newArray(int size) {
            return new ArrGenre[size];
        }
    };
}
