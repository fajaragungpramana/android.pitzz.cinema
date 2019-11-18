package com.implizstudio.android.app.pitzz.ui.adapter

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseAdapter
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.databinding.AdapterProductionBinding

class ProductionAdapter(context: Context?, private val listProduction: List<BaseEntity.Production>?)
    : BaseAdapter<AdapterProductionBinding, ProductionAdapter.ViewHolder>(context, listProduction) {

    override fun getContentView() = R.layout.adapter_production

    override fun getViewHolder(view: View) = ViewHolder(view)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) { holder.bindProduction(listProduction?.get(position)) }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bindProduction(production: BaseEntity.Production?) { getBinding().production = production }

    }

}