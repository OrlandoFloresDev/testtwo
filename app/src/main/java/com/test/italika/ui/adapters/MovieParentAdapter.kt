package com.test.italika.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.italika.databinding.MoviesListItemBinding
import com.test.italika.models.Movie
import kotlin.collections.ArrayList

class MovieParentAdapter(private val movies: ArrayList<Movie>) :
    RecyclerView.Adapter<MovieParentAdapter.ViewHolder>() {

    var onItemClick: ((Movie) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MoviesListItemBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(movies[position])
    }
    inner class ViewHolder(private val binding: MoviesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.movie = movie
            binding.executePendingBindings()
            binding.root.setOnClickListener{
                onItemClick?.invoke(movie)
            }
        }
    }
}

