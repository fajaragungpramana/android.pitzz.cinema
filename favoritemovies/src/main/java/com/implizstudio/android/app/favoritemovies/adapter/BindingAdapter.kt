package com.implizstudio.android.app.favoritemovies.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.implizstudio.android.app.favoritemovies.R
import com.implizstudio.android.app.favoritemovies.model.constant.Constant

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("displayPoster")
    fun ImageView.displayImage(poster: String?) {
        loadImageWithCoil(this, "${Constant.SIZE_POSTER}$poster")
    }

    private fun loadImageWithCoil(imageView: ImageView, url: String?) {
        imageView.load("${Constant.BASE_URL_IMAGE}$url") {
            placeholder(R.drawable.ic_no_image)
            error(R.drawable.ic_no_image)
            build()
        }
    }

}