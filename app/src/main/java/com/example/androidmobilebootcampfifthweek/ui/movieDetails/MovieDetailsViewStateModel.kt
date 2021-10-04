package com.example.androidmobilebootcampfifthweek.ui.movieDetails

import com.example.androidmobilebootcampfifthweek.models.MovieDetailsGetResponse

class MovieDetailsViewStateModel(val movieDetailsGetResponse: MovieDetailsGetResponse) {

    fun getMovieDetails(): MovieDetailsGetResponse = movieDetailsGetResponse

    fun getMovieDetailsTime(): String {

        val movieHour: Int = movieDetailsGetResponse.runtime / 60
        val movieMinutes: Int = movieDetailsGetResponse.runtime % 60

        return "${movieHour}h ${movieMinutes}min"
    }

    fun getMovieDetailsReleaseYear(): String {
        val movieReleaseYear: String = movieDetailsGetResponse.release_date.substring(0, 4)
        return movieReleaseYear
    }

    fun getMovieDetailsVoteAverage(): String {
        val voteAverage: String = movieDetailsGetResponse.vote_average.toString()
        return voteAverage
    }


}


