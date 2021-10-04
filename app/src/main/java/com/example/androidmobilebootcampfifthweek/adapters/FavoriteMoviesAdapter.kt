package com.example.androidmobilebootcampfifthweek.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.databinding.FavoriteMovieCardViewBinding
import com.example.androidmobilebootcampfifthweek.databinding.ProgressDialogBinding
import com.example.androidmobilebootcampfifthweek.models.FavoriteMovie

class FavoriteMoviesAdapter(
    private val favoriteMovies: MutableList<FavoriteMovie>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val FAVORITE_MOVIE_CARD_VIEW = 0
    private val PROGRESS_DIALOG = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == FAVORITE_MOVIE_CARD_VIEW)
            FavoriteMovieViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.favorite_movie_card_view,
                    parent,
                    false
                )
            )
        else
            ProgressDialogViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.progress_dialog,
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val favoriteMovie = this.favoriteMovies[position]

        when(getItemViewType(position)) {

            FAVORITE_MOVIE_CARD_VIEW -> {
                (holder as FavoriteMoviesAdapter.FavoriteMovieViewHolder).apply{
                    populate(favoriteMovie)
                }
            }

            PROGRESS_DIALOG-> {
                (holder as FavoriteMoviesAdapter.ProgressDialogViewHolder).apply {

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return this.favoriteMovies.size
    }

//    override fun getItemViewType(position: Int): Int {
//        return if (this.favoriteMovies[position].equals(null)) {
//            PROGRESS_DIALOG;
//        } else {
//            FAVORITE_MOVIE_CARD_VIEW;
//        }
//    }


    inner class FavoriteMovieViewHolder(private val binding: FavoriteMovieCardViewBinding): RecyclerView.ViewHolder(binding.root){

        fun populate(favoriteMovie: FavoriteMovie){
            binding.favoriteMovie = favoriteMovie
            binding.executePendingBindings()

        }

    }


    inner class ProgressDialogViewHolder(private val binding: ProgressDialogBinding) :
        RecyclerView.ViewHolder(binding.root)

}