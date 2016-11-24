package com.project.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by anandmishra on 23/11/16.
 */

@Generated ("org.jsonschema2pojo")
public class ArrGenre {

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

}
