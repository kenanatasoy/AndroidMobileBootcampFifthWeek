package com.example.androidmobilebootcampfifthweek.ui.movieDetails

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.androidmobilebootcampfifthweek.MainActivity
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.adapters.OnMovieDetailClickListener
import com.example.androidmobilebootcampfifthweek.base.BaseFragment
import com.example.androidmobilebootcampfifthweek.databinding.FragmentMovieDetailsBinding
import com.example.androidmobilebootcampfifthweek.models.FavoriteMovie
import com.example.androidmobilebootcampfifthweek.models.MovieFavoriteState
import com.google.gson.Gson

class MovieDetailsFragment : BaseFragment<MovieDetailsViewModel, FragmentMovieDetailsBinding>(), OnMovieDetailClickListener {

    override var viewModel: MovieDetailsViewModel? = null

    override fun getLayoutID() = R.layout.fragment_movie_details

    var movieId = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val args = this.arguments
        this.movieId = args?.getString("movieId")!!.toInt()
        Log.d("movieId =", movieId.toString())
        return super.onCreateView(inflater, container, savedInstanceState)

    }

    //region optional backbutton functionality
//    override fun onResume() {
//        super.onResume()
//
//        val activity = activity as MainActivity
//
//        activity.onBackPressedDispatcher.addCallback(viewLifecycleOwner, object :
//            OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                activity.finish()
//            }
//        })
//
//    }
    //endregion

    override fun observeLiveData() {

        viewModel!!.movieId = movieId
        viewModel!!.getMovieDetails()
        viewModel?.movieDetailsGetResponse?.observe(this, { movieDetailsViewStateModel ->
            dataBinding?.movieDetailsViewStateModel = movieDetailsViewStateModel

            dataBinding?.executePendingBindings()

            movieDetailsViewStateModel.getMovieDetails().genres.forEach { genre ->

                val view : View = LayoutInflater.from(requireContext()).inflate(R.layout.movie_genre, dataBinding!!.genreContainer, false)
                val movieGenre = view.findViewById<TextView>(R.id.movieGenre)

                movieGenre.text = genre.name

                dataBinding!!.genreContainer.addView(view)

                dataBinding?.isFavorite!!.isChecked = loadMovieFavoriteState(movieId)


            }

        })

    }


    override fun prepareView() {
        dataBinding?.clickHandler = this
    }


    override fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(MovieDetailsViewModel()::class.java)
    }

    override fun onBackButtonPressed() {
        findNavController().navigate(R.id.action_movieDetailsFragment_to_homeFragment)
    }

    override fun onMovieCheckFavoriteClicked(view: View) {
        when (view.id) {
            R.id.isFavorite -> {
                if(dataBinding?.isFavorite!!.isChecked){

                    saveMovieIdAndFavoriteState(dataBinding?.movieDetailsViewStateModel!!.getMovieDetails().id, dataBinding?.isFavorite!!.isChecked)
                    saveFavoriteMovieIdAndTitle(dataBinding?.movieDetailsViewStateModel!!.getMovieDetails().id, dataBinding!!.movieDetailsViewStateModel!!.getMovieDetails().title)
                }

                else if(!dataBinding?.isFavorite!!.isChecked) {

                    saveMovieIdAndFavoriteState(dataBinding?.movieDetailsViewStateModel!!.getMovieDetails().id, dataBinding?.isFavorite!!.isChecked)
                    removeFavoriteMovieIdAndTitle(dataBinding!!.movieDetailsViewStateModel!!.getMovieDetails().id, dataBinding!!.movieDetailsViewStateModel!!.getMovieDetails().title)
                }
            }
        }
    }



    fun loadMovieFavoriteState(movieId: Int): Boolean{

        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        var movieFavoriteStateSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieFavoriteStateSetInStorage", null)
        if (movieFavoriteStateSetInStorage == null){
            return false
        }

        else{

            for (i in 0 until movieFavoriteStateSetInStorage.size){

                var movieFavoriteStateObjectBack = Gson().fromJson(movieFavoriteStateSetInStorage.elementAt(i), MovieFavoriteState::class.java)

                if (movieFavoriteStateObjectBack.id == movieId){
                    return movieFavoriteStateObjectBack.isFavorite
                }

            }

            return false

        }

    }

    fun saveMovieIdAndFavoriteState(movieId: Int, movieFavoriteStateBool: Boolean){

        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        var movieFavoriteStateSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieFavoriteStateSetInStorage", null)
        val editor = sharedPreferences.edit()

        var movieFavoriteStateObject = MovieFavoriteState(movieId, movieFavoriteStateBool)
        val jsonStringMovieFavState = Gson().toJson(movieFavoriteStateObject)

        if (movieFavoriteStateSetInStorage == null){

            editor.remove("movieFavoriteStateSet")
            editor.commit()

            movieFavoriteStateSetInStorage = mutableSetOf()
            movieFavoriteStateSetInStorage.add(jsonStringMovieFavState)

            editor.apply{
                putStringSet("movieFavoriteStateSetInStorage", movieFavoriteStateSetInStorage)
            }.apply()

        }

        else {
            for (i in 0 until movieFavoriteStateSetInStorage.size){

                var movieFavoriteStateObjectBack = Gson().fromJson(movieFavoriteStateSetInStorage.elementAt(i), MovieFavoriteState::class.java)
                if (movieFavoriteStateObjectBack.id == movieId){
                    movieFavoriteStateObjectBack.isFavorite = !movieFavoriteStateObjectBack.isFavorite
                    val jsonStringMovieFavStateRet = Gson().toJson(movieFavoriteStateObjectBack)
                    movieFavoriteStateSetInStorage.remove(movieFavoriteStateSetInStorage.elementAt(i))
                    movieFavoriteStateSetInStorage.add(jsonStringMovieFavStateRet)

                    editor.remove("movieFavoriteStateSetInStorage")
                    editor.commit()

                    editor.apply{
                        putStringSet("movieFavoriteStateSetInStorage", movieFavoriteStateSetInStorage)
                    }.apply()

                    break
                }

            }

            movieFavoriteStateSetInStorage.add(jsonStringMovieFavState)

            editor.remove("movieFavoriteStateSetInStorage")
            editor.commit()

            editor.apply{
                putStringSet("movieFavoriteStateSetInStorage", movieFavoriteStateSetInStorage)
            }.apply()

        }


    }

    @SuppressLint("MutatingSharedPrefs")
    fun saveFavoriteMovieIdAndTitle(movieId: Int, movieTitle: String){

        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)
        var movieIdSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieIdSetInStorage", null)
        var movieTitleSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieTitleSetInStorage", null)

        if (movieIdSetInStorage == null){

            val editor = sharedPreferences.edit()
            editor.remove("movieIdSetInStorage")
            editor.remove("movieTitleSetInStorage")
            editor.commit()

            movieIdSetInStorage = mutableSetOf()
            movieTitleSetInStorage = mutableSetOf()

            movieIdSetInStorage.add(movieId.toString())
            movieTitleSetInStorage.add(movieTitle)

            editor.apply{
                putStringSet("movieIdSetInStorage", movieIdSetInStorage)
                putStringSet("movieTitleSetInStorage", movieTitleSetInStorage)
            }.commit()
        }

        else if(!movieIdSetInStorage.contains(movieId.toString())){


            movieIdSetInStorage.add(movieId.toString())
            movieTitleSetInStorage!!.add(movieTitle)


            val editor = sharedPreferences.edit()
            editor.remove("movieIdSetInStorage")
            editor.remove("movieTitleSetInStorage")
            editor.commit()

            editor.apply{
                putStringSet("movieIdSetInStorage", movieIdSetInStorage)
                putStringSet("movieTitleSetInStorage", movieTitleSetInStorage)
            }.commit()
        }

    }

    @SuppressLint("MutatingSharedPrefs")
    fun removeFavoriteMovieIdAndTitle(movieId: Int, movieTitle: String){

        val sharedPreferences = this.requireActivity().getSharedPreferences("sharedPrefs", Context.MODE_PRIVATE)

        var movieIdSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieIdSetInStorage", null)
        var movieTitleSetInStorage: MutableSet<String>? = sharedPreferences.getStringSet("movieTitleSetInStorage", null)

        if (movieIdSetInStorage != null && movieTitleSetInStorage != null){
            movieIdSetInStorage.remove(movieId.toString())
            movieTitleSetInStorage.remove(movieTitle)

            val editor = sharedPreferences.edit()
            editor.apply{
                putStringSet("movieIdSetInStorage", movieIdSetInStorage)
                putStringSet("movieTitleSetInStorage", movieTitleSetInStorage)
            }.apply()

        }

    }



}
