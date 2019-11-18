package com.implizstudio.android.app.pitzz.ui.tab.television

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseFragment
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.FragmentTabBinding
import com.implizstudio.android.app.pitzz.ui.adapter.TelevisionAdapter
import kotlinx.android.synthetic.main.fragment_tab.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteTelevisionTab : BaseFragment<FragmentTabBinding>(), TelevisionAdapter.Callback {

    private val viewModel: FavoriteTelevisionViewModel by viewModel()

    override fun getContentView() = R.layout.fragment_tab

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            isVisibleProgressBar().observe(viewLifecycleOwner, Observer { getBinding().isVisibleProgressBar = it })
            isVisibleNoTelevision().observe(viewLifecycleOwner, Observer { getBinding().isVisibleNoMovie = it })
        }

        srl_favorite.setOnRefreshListener { viewModel.isRefreshing().observe(viewLifecycleOwner, Observer { srl_favorite.isRefreshing = it }) }

    }

    override fun onResume() {
        super.onResume()

        viewModel.getTelevisions().observe(this, Observer {
            rv_favorite.apply {
                layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                adapter = TelevisionAdapter(activity, it, this@FavoriteTelevisionTab)
            }
        })

    }

    override fun onEnglishLanguage() {}

    override fun onIndonesiaLanguage() {}

    override fun onAdapterPosition(position: Int) {}

}