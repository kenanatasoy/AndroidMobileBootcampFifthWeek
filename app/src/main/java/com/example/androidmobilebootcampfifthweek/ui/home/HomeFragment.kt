package com.example.androidmobilebootcampfifthweek.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidmobilebootcampfifthweek.R
import com.example.androidmobilebootcampfifthweek.databinding.FragmentHomeBinding
import com.example.androidmobilebootcampfifthweek.adapters.ViewPagerAdapter
import com.example.androidmobilebootcampfifthweek.base.FragmentActions
import com.example.androidmobilebootcampfifthweek.ui.favoriteMovies.FavoriteMoviesFragment
import com.example.androidmobilebootcampfifthweek.ui.popularMovies.PopularMoviesFragment
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)

        adapter.addFragment(PopularMoviesFragment())
        adapter.addFragment(FavoriteMoviesFragment())

        val titleList = mutableListOf("Popular Movies", "Favorite Movies")
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = titleList[position]
            when(position){
                0 -> tab.setIcon(R.drawable.ic_movie_icon)
                1 -> tab.setIcon(R.drawable.ic_star_icon)
            }
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


