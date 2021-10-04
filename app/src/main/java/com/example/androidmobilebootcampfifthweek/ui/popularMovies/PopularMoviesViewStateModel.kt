package com.example.androidmobilebootcampfifthweek.ui.popularMovies

import com.example.androidmobilebootcampfifthweek.models.PopularMovie
import com.example.androidmobilebootcampfifthweek.models.PopularMoviesGetResponse

data class PopularMoviesViewStateModel(val popularMoviesGetResponse: PopularMoviesGetResponse){

    fun getPopularMoviesList() : ArrayList<PopularMovie> = popularMoviesGetResponse.popularMovies

}
