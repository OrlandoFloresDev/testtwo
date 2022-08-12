package com.test.italika.ui.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.italika.R
import com.test.italika.base.BaseFragment
import com.test.italika.databinding.FragmentMoviesBinding
import com.test.italika.models.Movie
import com.test.italika.ui.activities.MovieDetailActivity
import com.test.italika.ui.adapters.MovieParentAdapter
import com.test.italika.viewmodels.MoviesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MovieFragment(category: String) : BaseFragment() {
    private lateinit var binding: FragmentMoviesBinding

    private lateinit var newsRecycler: RecyclerView
    private lateinit var mainAdapter: MovieParentAdapter
    private lateinit var nestedScrollView: NestedScrollView
    private var requestCategory = category

    private lateinit var viewModel: MoviesViewModel

    private val yCoordinate = "y"

    companion object Factory {
        fun create(category: String): MovieFragment {
            return MovieFragment(category)
        }
    }

    @SuppressLint("CheckResult")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        binding = FragmentMoviesBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner

        initUI()
        observeMoviesData()
        scrollToPreviousPosition(savedInstanceState)

        return binding.root
    }

    private fun initUI() {
        newsRecycler = binding.moviesRecyclerView.also {
            with(it) {
                layoutManager = GridLayoutManager(context, 2)
                itemAnimator = DefaultItemAnimator()
            }
        }

        nestedScrollView = binding.nestedScrollView
    }

    private fun observeMoviesData() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it) showLoading() else hideLoading()
        }

        viewModel.loadData(requestCategory)
        viewModel.getMoviesList().observe(
            viewLifecycleOwner
        ) {
            it?.let {
                if (it == ArrayList(Collections.EMPTY_LIST)) {
                    Snackbar.make(
                        binding.root,
                        getString(R.string.internet_connection_lost),
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    drawRecyclerView(it)
                }
            }
        }
    }

    private fun drawRecyclerView(movies: ArrayList<Movie>) {
        mainAdapter = MovieParentAdapter(movies)
        newsRecycler.adapter = mainAdapter
        mainAdapter.notifyDataSetChanged()
        mainAdapter.onItemClick = { movie ->
            run {
                Log.d("TAG_TEST", movie.overview)
                val intent = Intent(context, MovieDetailActivity::class.java)
                intent.putExtra("EXTRA_MOVIE", movie)
                startActivity(intent)
            }
        }
    }

    private fun scrollToPreviousPosition(bundle: Bundle?) {
        bundle?.let {
            Handler().postDelayed({
                nestedScrollView.scrollTo(0, it.getInt(yCoordinate))
            }, 700)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(yCoordinate, nestedScrollView.scrollY)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.getMoviesList().removeObservers(viewLifecycleOwner)
    }
}