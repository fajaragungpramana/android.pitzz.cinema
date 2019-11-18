package com.implizstudio.android.app.pitzz.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.implizstudio.android.app.pitzz.data.model.BaseEntity

/**
 * @param<B> - Mean layout binding
 * @param<H> - Mean recycler view holder
 */
abstract class BaseAdapter<B: ViewDataBinding, H: RecyclerView.ViewHolder>(private val context: Context?, private val listItem: List<BaseEntity>?)
    : RecyclerView.Adapter<H>() {

    private lateinit var binding: B

    protected abstract fun getContentView(): Int
    protected abstract fun getViewHolder(view: View): H

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getContentView(), parent, false)
        return getViewHolder(binding.root)
    }

    override fun getItemCount() = listItem?.size ?: 0

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemViewType(position: Int) = position

    protected fun getBinding() = binding

}