package com.example.androidmobilebootcampfifthweek.ui.popularMovies

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.androidmobilebootcampfifthweek.repository.PopularMoviesRepository
import com.example.androidmobilebootcampfifthweek.utils.API_KEY

class PopularMoviesViewModel: ViewModel() {

    val popularMoviesGetResponse = MediatorLiveData<PopularMoviesViewStateModel>()
    val popularMoviesRepository = PopularMoviesRepository()

    var page = 1

    init {
        popularMoviesRepository.getPopularMovies(API_KEY, "en-US", page)
        popularMoviesGetResponse.addSource(popularMoviesRepository.onPopularMoviesFetched) {
            popularMoviesGetResponse.value = PopularMoviesViewStateModel(it)
        }
    }

    fun getPopularMovies(){
        popularMoviesRepository.getPopularMovies(API_KEY, "en-US", ++page)
    }

}

