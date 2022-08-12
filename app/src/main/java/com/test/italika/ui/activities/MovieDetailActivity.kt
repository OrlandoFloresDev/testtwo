package com.test.italika.ui.activities

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.test.italika.R
import com.test.italika.databinding.ActivityMovieDetailBinding
import com.test.italika.models.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        val movie = intent.getSerializableExtra("EXTRA_MOVIE") as? Movie
        if (movie != null) {
            binding.movie = movie
            binding.executePendingBindings()
            Log.i("EXTRA_MOVIE", movie.overview)
        }
        initUI()

    }

    private fun initUI() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        val tf: Typeface? = ResourcesCompat.getFont(this, R.font.carme);
        binding.collapsingToolbar.setCollapsedTitleTypeface(tf)
        binding.collapsingToolbar.setExpandedTitleTypeface(tf)
    }
}
