package com.project.network.views;

import com.project.models.ArrGenre;
import com.project.models.MovieResultsDiscover;
import com.project.models.MovieResultsTopRated;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;
import rx.Single;

/**
 * Created by anandmishra on 12/11/16.
 */

public interface MovieDbApiInterface {

    @GET("3/movie/top_rated")
    Observable<MovieResultsTopRated> getMoviesList(@Query("api_key") String apiKey);

    @GET("3/genre/movie/list")
    Observable<ArrGenre> getGenreList(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/genre/movie/list")
    Single<ArrGenre> getSingleGenreList(@Query("api_key") String apiKey, @Query("language") String language);

    @GET("3/discover/{type}")
    Observable<MovieResultsDiscover> getMovieDiscoverList(@Path("type") String type,
                                                          @Query("api_key") String apiKey,
                                                          @Query("language") String language,
                                                          @Query("with_genres") String genres,
                                                          @Query("page") Integer page,
                                                          @Query("sort_by") String poularity);

//    @GET("3/genre/movie/list?api_key=18de281f75e7396d4de4c82d0fc79d29&language=en-US")

}
