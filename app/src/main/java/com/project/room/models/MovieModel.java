package com.project.room.models;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;

import com.project.room.utils.DateConverter;

import java.util.Date;

/**
 * Created by anandmishra on 11/08/17.
 */


@Entity
public class MovieModel {

    @PrimaryKey(autoGenerate = true)
    public int movieId;

    public String name;

    @TypeConverters (DateConverter.class)
    Date releaseDate;

    public MovieModel(String name, Date releaseDate) {
        this.name = name;
        this.releaseDate = releaseDate;
    }

    public String getName() {
        return this.name;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public int getMovieId() {
        return this.movieId;
    }

}
