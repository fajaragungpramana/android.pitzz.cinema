package com.implizstudio.android.app.pitzz.ui.bnv.movie

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseFragment
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.FragmentMovieBinding
import com.implizstudio.android.app.pitzz.ui.adapter.MovieAdapter
import com.implizstudio.android.app.pitzz.ui.search.movie.SearchMovieActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MovieFragment : BaseFragment<FragmentMovieBinding>(), MovieAdapter.Callback {

    private val viewModel: MovieViewModel by viewModel()

    override fun getContentView() = R.layout.fragment_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.movies)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            isVisibleShimmer().observe(viewLifecycleOwner, Observer { getBinding().isVisibleShimmer = it })
            isVisibleProgressBar().observe(viewLifecycleOwner, Observer { getBinding().isVisibleProgressBar = it })
        }

        srl_movie.setOnRefreshListener { viewModel.isRefreshing().observe(viewLifecycleOwner, Observer { srl_movie.isRefreshing = it }) }
        rv_movie.scrollToPosition(viewModel.getLastVisibleItem())

        viewModel.getMovies()?.observe(viewLifecycleOwner, Observer {

            rv_movie.apply {
                layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                adapter = MovieAdapter(activity, it, this@MovieFragment)
                scrollToPosition(viewModel.getLastVisibleItem())
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.action_search).actionView as SearchView
        search.apply {
            queryHint = getString(R.string.looking_a_movie)
            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String?) = false

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) activity?.startActivity<SearchMovieActivity>(Constant.Key.SEARCH to query)
                    return true
                }

            })
        }

    }

    override fun onEnglishLanguage() { viewModel.setQueryLanguage(Constant.Key.LANGUAGE_ENGLISH) }

    override fun onIndonesiaLanguage() { viewModel.setQueryLanguage(Constant.Key.LANGUAGE_INDONESIA) }

    override fun onAdapterPosition(position: Int) { viewModel.onLastVisibleItemReached(position) }

}