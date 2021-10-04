package com.example.androidmobilebootcampfifthweek.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidmobilebootcampfifthweek.base.BaseCallBack
import com.example.androidmobilebootcampfifthweek.models.PopularMoviesGetResponse
import com.example.androidmobilebootcampfifthweek.network.ServiceConnector

class PopularMoviesRepository {

    var onPopularMoviesFetched = MutableLiveData<PopularMoviesGetResponse>()

    fun getPopularMovies(apiKey : String, language: String, page: Int){

        ServiceConnector.restInterface.getPopularMovies(apiKey, language, page).enqueue(object  : BaseCallBack<PopularMoviesGetResponse>(){
            override fun onSuccess(data: PopularMoviesGetResponse) {
                onPopularMoviesFetched.value = data
            }

        })
    }

}