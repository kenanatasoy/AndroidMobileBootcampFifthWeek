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

class PopularMoviesAdapter(
    private val popularMovies: List<PopularMovie>,
    private val clickListener: OnPopularMovieClickListener
) : RecyclerView.Adapter<PopularMoviesAdapter.BaseItemHolder>() {

    companion object {
        private const val PROGRESS_DIALOG = 1
        private const val MOVIE_CARD_VIEW = 2
    }

    private var popularMoviesList = popularMovies
    private var showLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseItemHolder {
        when (viewType) {
            MOVIE_CARD_VIEW -> return PopularMovieViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.popular_movie_card_view,
                    parent,
                    false
                )
            )
            PROGRESS_DIALOG -> return ProgressDialogViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.progress_dialog,
                    parent,
                    false
                )
            )
        }
        return BaseItemHolder(parent)
    }

    override fun getItemCount(): Int {
        // return popularMovies size + progress dialog size
        return popularMoviesList.size + if (showLoading) {
            1
        } else {
            0
        }
    }

    override fun onBindViewHolder(holder: BaseItemHolder, position: Int) {
        if (holder is PopularMovieViewHolder) {
            holder.bind(popularMoviesList[position])
        } else if (holder is ProgressDialogViewHolder) {

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position < popularMoviesList.size - 1) {
            return MOVIE_CARD_VIEW
        }
        return PROGRESS_DIALOG
    }

    fun hideLoading() {
        showLoading = false
        //be careful with the index
        notifyItemRemoved(itemCount)
    }

    fun showLoading() {
        showLoading = true
        //be careful with the index
        notifyItemInserted(itemCount - 1)
    }

    open class BaseItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class ProgressDialogViewHolder(private val progressDialogBinding: ProgressDialogBinding) :
        BaseItemHolder(progressDialogBinding.root)


    inner class PopularMovieViewHolder(
        private val popularMovieCardViewBinding: PopularMovieCardViewBinding
    ) : PopularMoviesAdapter.BaseItemHolder(popularMovieCardViewBinding.root), View.OnClickListener {

        init {
            popularMovieCardViewBinding.cardView.setOnClickListener(this)
        }

        fun bind(popularMovie: PopularMovie) {
            popularMovieCardViewBinding.popularMovie = popularMovie
            popularMovieCardViewBinding.executePendingBindings()
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                clickListener.onPopularMovieClick(position)
            }
        }


    }



}



