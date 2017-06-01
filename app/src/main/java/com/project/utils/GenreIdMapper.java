package com.project.utils;

import java.util.List;

/**
 * Created by anandmishra on 02/06/17.
 */

public class GenreIdMapper {

    public static String getGenreNameFromId(Integer id) {
        String name = "";
        switch (id) {
            case 28:
                name = "Action";
                break;
            case 12:
                name = "Adventure";
                break;
            case 16:
                name = "Animation";
                break;
            case 35:
                name = "Comedy";
                break;
            case 80:
                name = "Crime";
                break;
            case 99:
                name = "Documentary";
                break;
            case 18:
                name = "Drama";
                break;
            case 10751:
                name = "Family";
                break;
            case 14:
                name = "Fantasy";
                break;
            case 36:
                name = "History";
                break;
            case 27:
                name = "Horror";
                break;
            case 10402:
                name = "Music";
                break;
            case 9648:
                name = "Mystery";
                break;
            case 10749:
                name = "Romance";
                break;
            case 878:
                name = "Science Fiction";
                break;
            case 10770:
                name = "TV Movie";
                break;
            case 53:
                name = "Thriller";
                break;
            case 10752:
                name = "War";
                break;
            case 37:
                name = "Western";
                break;
        }
        return name;
    }

    public static String getGenreNamesFromIds(List<Integer> genreIds) {
        StringBuilder genres = new StringBuilder();
        for (Integer id : genreIds){
            genres.append(getGenreNameFromId(id));
            if (!id.equals(genreIds.get(genreIds.size() - 1))){
                genres.append(", ");
            }
        }
        return genres.toString();
    }
}


