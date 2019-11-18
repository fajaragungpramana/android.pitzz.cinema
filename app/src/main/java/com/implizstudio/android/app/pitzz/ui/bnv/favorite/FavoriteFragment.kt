package com.implizstudio.android.app.pitzz.ui.bnv.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.ui.adapter.TabAdapter
import com.implizstudio.android.app.pitzz.ui.tab.movie.FavoriteMovieTab
import com.implizstudio.android.app.pitzz.ui.tab.television.FavoriteTelevisionTab
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        activity?.title = getString(R.string.favorites)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vp_favorite.let {
            it.adapter = TabAdapter(childFragmentManager).apply {
                setup(FavoriteMovieTab(), getString(R.string.movies))
                setup(FavoriteTelevisionTab(), getString(R.string.televisions))
            }

            tl_favorite.setupWithViewPager(it)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.findItem(R.id.action_search).isVisible = false
    }

}