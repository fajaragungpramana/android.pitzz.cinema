package com.implizstudio.android.app.favoritemovies.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.implizstudio.android.app.favoritemovies.R
import com.implizstudio.android.app.favoritemovies.databinding.AdapterMovieBinding
import com.implizstudio.android.app.favoritemovies.model.Movie

class MovieAdapter(private val context: Context?, private val listMovie: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private lateinit var binding: AdapterMovieBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.adapter_movie, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount() = listMovie.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bindMovie(listMovie[position]) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindMovie(movie: Movie) { binding.movie = movie }

    }

}