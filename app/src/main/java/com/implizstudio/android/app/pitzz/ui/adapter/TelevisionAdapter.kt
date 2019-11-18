package com.implizstudio.android.app.pitzz.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseAdapter
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.AdapterMovieBinding
import com.implizstudio.android.app.pitzz.ui.detail.television.DetailTelevisionActivity
import kotlinx.android.synthetic.main.adapter_movie.view.*
import org.jetbrains.anko.startActivity

class TelevisionAdapter(private val context: Context?, private val listTelevision: List<BaseEntity.Television>?, private val callback: Callback)
    : BaseAdapter<AdapterMovieBinding, TelevisionAdapter.ViewHolder>(context, listTelevision) {

    override fun getContentView() = R.layout.adapter_movie

    override fun getViewHolder(view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindTelevision(listTelevision?.get(position))

        callback.onAdapterPosition(position)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bindTelevision(television: BaseEntity.Television?) {
            getBinding().television = television
            view.cv_poster.setOnClickListener { context?.startActivity<DetailTelevisionActivity>(Constant.Key.MOVIE_ID to television?.id) }
        }

    }

    interface Callback { fun onAdapterPosition(position: Int) }

}