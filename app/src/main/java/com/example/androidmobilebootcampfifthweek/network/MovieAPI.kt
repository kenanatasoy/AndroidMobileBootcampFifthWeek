package com.example.androidmobilebootcampfifthweek.network

import com.example.androidmobilebootcampfifthweek.models.MovieDetailsGetResponse
import com.example.androidmobilebootcampfifthweek.models.PopularMoviesGetResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface MovieAPI {

    @GET("popular")
    fun getPopularMovies(@Query("api_key") apiKey : String, @Query("language") language: String, @Query("page") page: Int): Call<PopularMoviesGetResponse>

    @GET("{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int, @Query("api_key") apiKey: String, @Query("language") language: String): Call<MovieDetailsGetResponse>


}
