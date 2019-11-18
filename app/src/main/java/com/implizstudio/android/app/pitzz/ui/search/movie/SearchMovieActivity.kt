package com.implizstudio.android.app.pitzz.ui.search.movie

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseActivity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.ActivitySearchBinding
import com.implizstudio.android.app.pitzz.ui.adapter.MovieAdapter
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.android.synthetic.main.support_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchMovieActivity : BaseActivity<ActivitySearchBinding>(), MovieAdapter.Callback {

    private val viewModel: SearchMovieViewModel by viewModel()

    private var movieTitle: String? = null

    override fun getContentView() = R.layout.activity_search

    override fun onCreated(savedInstanceState: Bundle?) {
        movieTitle = intent.getStringExtra(Constant.Key.SEARCH)

        setSupportActionBar(support_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.apply {
            isVisibleShimmer().observe(this@SearchMovieActivity, Observer { getBinding().isVisibleShimmer = it })
            isVisibleProgressBar().observe(this@SearchMovieActivity, Observer { getBinding().isVisibleProgressBar = it })
            isVisibleNoMovie().observe(this@SearchMovieActivity, Observer { getBinding().isVisibleNoMovie = it })
        }

        srl_movie.setOnRefreshListener { viewModel.isRefreshing().observe(this, Observer { srl_movie.isRefreshing = it }) }
        rv_movie.scrollToPosition(viewModel.getLastVisibleItem())

    }

    override fun onResume() {
        super.onResume()

        viewModel.getMovies(movieTitle)?.observe(this, Observer {
            rv_movie.apply {
                layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                adapter = MovieAdapter(this@SearchMovieActivity, it, this@SearchMovieActivity)
                scrollToPosition(viewModel.getLastVisibleItem())
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        menu?.findItem(R.id.action_settings)?.isVisible = false
        
        val search = menu?.findItem(R.id.action_search)?.actionView as SearchView
        search.apply { 
            queryHint = getString(R.string.looking_a_movie)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.searchMovie(newText)
                    return true
                }

                override fun onQueryTextSubmit(query: String?) = false

            })
        }
        
        return super.onCreateOptionsMenu(menu)
    }

    override fun onAdapterPosition(position: Int) { viewModel.onLastVisibleItemReached(position) }

}