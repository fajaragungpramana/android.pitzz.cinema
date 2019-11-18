package com.implizstudio.android.app.pitzz.ui.detail.television

import android.os.Build
import android.os.Bundle
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.api.load
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseActivity
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.ActivityDetailBinding
import com.implizstudio.android.app.pitzz.extension.invisible
import com.implizstudio.android.app.pitzz.extension.visible
import com.implizstudio.android.app.pitzz.ui.OnClickHandle
import com.implizstudio.android.app.pitzz.ui.adapter.ProductionAdapter
import kotlinx.android.synthetic.main.activity_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailTelevisionActivity : BaseActivity<ActivityDetailBinding>() {

    private val viewModel: DetailTelevisionViewModel by viewModel()

    private var televisionId: Int? = null

    override fun getContentView(): Int = R.layout.activity_detail

    override fun onCreated(savedInstanceState: Bundle?) {
        setStatusBarColor(Constant.Var.LIGHT_STATUS_BAR)
        televisionId = intent.getIntExtra(Constant.Key.MOVIE_ID, 0)

        getBinding().handle = OnClickHandle(this)
        viewModel.apply {
            isVisibleProgressBar().observe(this@DetailTelevisionActivity, Observer { getBinding().isProgressBarVisible = it })

            isTelevisionFavorite(televisionId).observe(this@DetailTelevisionActivity, Observer {
                iv_favorite.apply { if (it) load(R.drawable.ic_added_favorite) else load(R.drawable.ic_add_favorite) }
            })
        }

        srl_detail.setOnRefreshListener { viewModel.isRefreshing(televisionId).observe(this, Observer { srl_detail.isRefreshing = it }) }
        rv_production.layoutManager = StaggeredGridLayoutManager(Constant.Var.SPAN_PRODUCTION, StaggeredGridLayoutManager.VERTICAL)

        nsv_detail.setOnScrollChangeListener { _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _: Int ->

            when (scrollY) {

                in 0..10 -> {
                    setStatusBarColor(Constant.Var.LIGHT_STATUS_BAR)
                    v_swipe.alpha = 0.0F
                }
                in 10..20 -> v_swipe.alpha = 0.1F
                in 20..30 -> v_swipe.alpha = 0.2F
                in 30..40 -> v_swipe.alpha = 0.3F
                in 40..50 -> v_swipe.alpha = 0.4F
                in 50..60 -> v_swipe.alpha = 0.5F
                in 60..70 -> v_swipe.alpha = 0.6F
                in 70..80 -> v_swipe.alpha = 0.7F
                in 80..90 -> v_swipe.alpha = 0.8F
                in 90..100 -> v_swipe.alpha = 0.9F
                in 100..200 -> {
                    v_swipe.alpha = 1.0F

                    tv_title.invisible()
                    iv_back.setImageResource(R.drawable.ic_back_white)

                }

                in 200..300 -> {
                    setStatusBarColor(Constant.Var.DARK_STATUS_BAR)

                    tv_title.visible()
                    iv_back.setImageResource(R.drawable.ic_back_black)
                }

            }

        }

    }

    override fun onResume() {
        super.onResume()

        viewModel.getTelevision(televisionId)?.observe(this, Observer { television ->
            getBinding().movie = television
            rv_production.adapter = ProductionAdapter(this, television.productions)

            iv_favorite.setOnClickListener { viewModel.favoriteListener(BaseEntity.Television(null, television.id, television.poster, television.name)) }
        })

    }

    override fun onEnglishLanguage() {
        super.onEnglishLanguage()
        viewModel.setQueryLanguage(Constant.Key.LANGUAGE_ENGLISH)
    }

    override fun onIndonesiaLanguage() {
        super.onIndonesiaLanguage()
        viewModel.setQueryLanguage(Constant.Key.LANGUAGE_INDONESIA)
    }

    private fun setStatusBarColor(hexValue: Int) { if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) window.decorView.systemUiVisibility = hexValue }

}