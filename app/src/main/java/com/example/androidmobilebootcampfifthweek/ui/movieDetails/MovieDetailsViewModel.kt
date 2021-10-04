package com.example.androidmobilebootcampfifthweek.ui.movieDetails

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmobilebootcampfifthweek.repository.MovieDetailsRepository
import com.example.androidmobilebootcampfifthweek.utils.API_KEY

class MovieDetailsViewModel: ViewModel() {

    val movieDetailsGetResponse = MediatorLiveData<MovieDetailsViewStateModel>()
    val movieDetailsRepository = MovieDetailsRepository()

    var movieId: Int = 0

    init {

        movieDetailsGetResponse.addSource(movieDetailsRepository.onMovieDetailsFetched) {
            movieDetailsGetResponse.value = MovieDetailsViewStateModel(it)
        }
    }

    fun getMovieDetails(){
        movieDetailsRepository.getMovieDetails(API_KEY, movieId)
    }

}

