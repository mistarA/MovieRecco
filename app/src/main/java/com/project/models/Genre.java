package com.project.models;

/**
 * Created by anandmishra on 23/11/16.
 */

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated ("org.jsonschema2pojo")
public class Genre {

    @SerializedName ("id")
    @Expose
    private Integer id;
    @SerializedName ("name")
    @Expose
    private String name;

    @Override
    public String toString() {
        return name;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name The name
     */
    public void setName(String name) {
        this.name = name;
    }

}


