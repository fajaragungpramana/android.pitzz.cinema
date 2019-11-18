package com.implizstudio.android.app.pitzz.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseAdapter
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.AdapterMovieBinding
import com.implizstudio.android.app.pitzz.ui.detail.movie.DetailMovieActivity
import kotlinx.android.synthetic.main.adapter_movie.view.*
import org.jetbrains.anko.startActivity

class MovieAdapter(private val context: Context?, private val listMovie: List<BaseEntity.Movie>?, private val callback: Callback)
    : BaseAdapter<AdapterMovieBinding, MovieAdapter.ViewHolder>(context, listMovie) {

    override fun getContentView() = R.layout.adapter_movie

    override fun getViewHolder(view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindMovie(listMovie?.get(position))

        callback.onAdapterPosition(position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindMovie(movie: BaseEntity.Movie?) {
            getBinding().movie = movie
            view.cv_poster.setOnClickListener { context?.startActivity<DetailMovieActivity>(Constant.Key.MOVIE_ID to movie?.id) }
        }

    }

    interface Callback { fun onAdapterPosition(position: Int) }

}