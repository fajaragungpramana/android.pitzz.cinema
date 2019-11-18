package com.implizstudio.android.app.pitzz.ui.search.television

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseActivity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.ActivitySearchBinding
import com.implizstudio.android.app.pitzz.ui.adapter.TelevisionAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.support_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchTelevisionActivity : BaseActivity<ActivitySearchBinding>(), TelevisionAdapter.Callback {

    private val viewModel: SearchTelevisionViewModel by viewModel()

    private var televisionTitle: String? = null

    override fun getContentView() = R.layout.activity_search

    override fun onCreated(savedInstanceState: Bundle?) {
        televisionTitle = intent.getStringExtra(Constant.Key.SEARCH)

        setSupportActionBar(support_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.apply {
            isVisibleShimmer().observe(this@SearchTelevisionActivity, Observer { getBinding().isVisibleShimmer = it })
            isVisibleProgressBar().observe(this@SearchTelevisionActivity, Observer { getBinding().isVisibleProgressBar = it })
            isVisibleNoTelevision().observe(this@SearchTelevisionActivity, Observer { getBinding().isVisibleNoMovie = it })
        }

        srl_movie.setOnRefreshListener { viewModel.isRefreshing().observe(this, Observer { srl_movie.isRefreshing = it }) }
        rv_movie.scrollToPosition(viewModel.getLastVisibleItem())

    }

    override fun onResume() {
        super.onResume()

        viewModel.getTelevisions(televisionTitle)?.observe(this, Observer {
            rv_movie.apply {
                layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                adapter = TelevisionAdapter(this@SearchTelevisionActivity, it, this@SearchTelevisionActivity)
                scrollToPosition(viewModel.getLastVisibleItem())
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        menu?.findItem(R.id.action_settings)?.isVisible = false

        val search = menu?.findItem(R.id.action_search)?.actionView as SearchView
        search.apply {
            queryHint = getString(R.string.looking_a_tv_shows)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchTelevision(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String?) = false

            })
        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onAdapterPosition(position: Int) { viewModel.onLastVisibleItemReached(position) }

}