package com.example.androidmobilebootcampfifthweek.models

import com.google.gson.annotations.SerializedName

data class PopularMoviesGetResponse(
    var page: Int,
    @SerializedName("results")
    val popularMovies: ArrayList<PopularMovie>,
    val total_pages: Int,
    val total_results: Int
)
