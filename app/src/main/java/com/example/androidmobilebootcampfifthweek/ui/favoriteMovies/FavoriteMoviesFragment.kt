package com.example.androidmobilebootcampfifthweek.ui.favoriteMovies

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.adapters.FavoriteMoviesAdapter
import com.example.androidmobilebootcampfifthweek.base.BaseFragment
import com.example.androidmobilebootcampfifthweek.databinding.FragmentFavoriteMoviesBinding
import com.example.androidmobilebootcampfifthweek.models.FavoriteMovie

class FavoriteMoviesFragment : BaseFragment<FavoriteMoviesViewModel, FragmentFavoriteMoviesBinding>() {

    override var viewModel: FavoriteMoviesViewModel? = null

    var ultimateFavoriteMoviesList =  ArrayList<FavoriteMovie>()

    override fun getLayoutID() = R.layout.fragment_favorite_movies

    override fun observeLiveData() {
    }

    override fun prepareView() {

        ultimateFavoriteMoviesList.addAll(getFavoriteMovies())

        dataBinding?.favoriteMoviesRecyclerView?.adapter =
            FavoriteMoviesAdapter(ultimateFavoriteMoviesList)
        dataBinding?.favoriteMoviesRecyclerView?.layoutManager = LinearLayoutManager(requireContext())


    }

    override fun prepareViewModel() {
    }


    fun getFavoriteMovies(): MutableList<FavoriteMovie>{

        var favoriteMovies =  ArrayList<FavoriteMovie>()

        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        val movieIdSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieIdSetInStorage", null)
        val movieTitleSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieTitleSetInStorage", null)

        if (movieIdSetInStorage != null && movieTitleSetInStorage != null){
            for (i in 0 until movieIdSetInStorage.size){
                val favoriteMovie = FavoriteMovie(movieIdSetInStorage.elementAt(i).toInt(), movieTitleSetInStorage.elementAt(i))
                favoriteMovies.add(favoriteMovie)
            }
            return favoriteMovies
        }

        return favoriteMovies

    }




}
