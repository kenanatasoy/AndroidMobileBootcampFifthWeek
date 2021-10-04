package com.example.androidmobilebootcampfifthweek.repository

import androidx.lifecycle.MutableLiveData
import com.example.androidmobilebootcampfifthweek.base.BaseCallBack
import com.example.androidmobilebootcampfifthweek.models.MovieDetailsGetResponse
import com.example.androidmobilebootcampfifthweek.network.ServiceConnector

class MovieDetailsRepository {

    val onMovieDetailsFetched = MutableLiveData<MovieDetailsGetResponse>()

    fun getMovieDetails(apiKey : String, movieId: Int){

        ServiceConnector.restInterface.getMovieDetails(movieId, apiKey, "en-US").enqueue(object  : BaseCallBack<MovieDetailsGetResponse>(){
            override fun onSuccess(data: MovieDetailsGetResponse) {
                onMovieDetailsFetched.postValue(data)
            }

        })
    }

}
