package com.example.androidmobilebootcampfifthweek.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.databinding.PopularMovieCardViewBinding
import com.example.androidmobilebootcampfifthweek.databinding.ProgressDialogBinding
import com.example.androidmobilebootcampfifthweek.models.PopularMovie

class OptionalPopularMoviesAdapter(
    private val popularMovies: List<PopularMovie>,
    private val listener: OnPopularMovieClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val MOVIE_CARD_VIEW = 0
    private val PROGRESS_DIALOG = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == MOVIE_CARD_VIEW)
            PopularMovieViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.popular_movie_card_view,
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

        val movie = this.popularMovies[position]

        when(getItemViewType(position)) {
            MOVIE_CARD_VIEW -> {
                (holder as PopularMovieViewHolder).apply{
                    populate(movie)
                }
            }

            PROGRESS_DIALOG-> {
                (holder as ProgressDialogViewHolder).apply {

                }
            }
        }
    }

    override fun getItemCount() = this.popularMovies.size


    inner class PopularMovieViewHolder(private val binding: PopularMovieCardViewBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.cardView.setOnClickListener(this)
        }

        fun populate(popularMovie: PopularMovie) {
            binding.popularMovie = popularMovie
            binding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onPopularMovieClick(position)
            }
        }
    }

    inner class ProgressDialogViewHolder(private val binding: ProgressDialogBinding) :
        RecyclerView.ViewHolder(binding.root)

}

