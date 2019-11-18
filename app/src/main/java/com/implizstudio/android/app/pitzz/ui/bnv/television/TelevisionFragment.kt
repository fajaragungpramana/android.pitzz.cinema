package com.implizstudio.android.app.pitzz.ui.bnv.television

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseFragment
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.FragmentMovieBinding
import com.implizstudio.android.app.pitzz.ui.adapter.TelevisionAdapter
import com.implizstudio.android.app.pitzz.ui.search.television.SearchTelevisionActivity
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.startActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class TelevisionFragment : BaseFragment<FragmentMovieBinding>(), TelevisionAdapter.Callback {

    private val viewModel: TelevisionViewModel by viewModel()

    override fun getContentView() = R.layout.fragment_movie

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.title = getString(R.string.televisions)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.apply {
            isVisibleShimmer().observe(viewLifecycleOwner, Observer { getBinding().isVisibleShimmer = it })
            isVisibleProgressBar().observe(viewLifecycleOwner, Observer { getBinding().isVisibleProgressBar = it })
        }

        srl_movie.setOnRefreshListener { viewModel.isRefreshing().observe(viewLifecycleOwner, Observer { srl_movie.isRefreshing = it }) }
        rv_movie.scrollToPosition(viewModel.getLastVisibleItem())

        viewModel.getTelevisions()?.observe(viewLifecycleOwner, Observer {

            rv_movie.apply {
                layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_MOVIE, StaggeredGridLayoutManager.VERTICAL)
                adapter = TelevisionAdapter(activity, it, this@TelevisionFragment)
                scrollToPosition(viewModel.getLastVisibleItem())
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val search = menu.findItem(R.id.action_search).actionView as SearchView
        search.apply {
            queryHint = getString(R.string.looking_a_tv_shows)
            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextChange(newText: String?) = false

                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query != null) activity?.startActivity<SearchTelevisionActivity>(Constant.Key.SEARCH to query)
                    return true
                }

            })
        }

    }

    override fun onEnglishLanguage() { viewModel.setQueryLanguage(Constant.Key.LANGUAGE_ENGLISH) }

    override fun onIndonesiaLanguage() { viewModel.setQueryLanguage(Constant.Key.LANGUAGE_INDONESIA) }

    override fun onAdapterPosition(position: Int) { viewModel.onLastVisibleItemReached(position) }

}