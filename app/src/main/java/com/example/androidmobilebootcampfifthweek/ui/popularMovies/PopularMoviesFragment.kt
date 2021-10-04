package com.example.androidmobilebootcampfifthweek.ui.popularMovies

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.adapters.PopularMoviesAdapter
import com.example.androidmobilebootcampfifthweek.adapters.OnPopularMovieClickListener
import com.example.androidmobilebootcampfifthweek.base.BaseFragment
import com.example.androidmobilebootcampfifthweek.databinding.FragmentPopularMoviesBinding
import com.example.androidmobilebootcampfifthweek.models.PopularMovie
import com.example.androidmobilebootcampfifthweek.ui.movieDetails.MovieDetailsFragment


class PopularMoviesFragment : BaseFragment<PopularMoviesViewModel, FragmentPopularMoviesBinding>(), OnPopularMovieClickListener {

    override fun getLayoutID() = R.layout.fragment_popular_movies
    override var viewModel: PopularMoviesViewModel? = null
    private var popularMovieList = ArrayList<PopularMovie>()

    override fun observeLiveData() {
        viewModel?.popularMoviesGetResponse?.observe(this, {
            dataBinding?.popularMoviesViewStateModel = it
            dataBinding?.executePendingBindings()

            popularMovieList.addAll(it.getPopularMoviesList())

            if(dataBinding?.popularMoviesRecyclerView?.adapter == null){
                dataBinding?.popularMoviesRecyclerView?.adapter = PopularMoviesAdapter(popularMovieList, this@PopularMoviesFragment)
            }

            else{
                dataBinding?.popularMoviesRecyclerView?.adapter!!.notifyDataSetChanged()
            }
        })
    }

    override fun prepareView() {
        dataBinding?.popularMoviesRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        dataBinding?.popularMoviesRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState== RecyclerView.SCROLL_STATE_IDLE) {

                    viewModel?.getPopularMovies()
                }
            }
        })
    }

    override fun prepareViewModel() {
        viewModel = ViewModelProvider(this).get(PopularMoviesViewModel::class.java)
    }

    override fun onPopularMovieClick(position: Int) {

        val clickedItem = popularMovieList[position]

        val bundle = Bundle()
        bundle.putString("movieId", clickedItem.id.toString())
        val movieDetailsFragment = MovieDetailsFragment()
        movieDetailsFragment.arguments = bundle

        findNavController().navigate(R.id.action_homeFragment_to_movieDetailsFragment, bundle)
    }


}

