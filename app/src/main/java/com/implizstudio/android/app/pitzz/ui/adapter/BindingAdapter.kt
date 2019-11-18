package com.implizstudio.android.app.pitzz.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.extension.gone
import com.implizstudio.android.app.pitzz.extension.visible
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapter {

    @JvmStatic
    @BindingAdapter("visibilityMode")
    fun View.visibilityMode(isVisible: Boolean?) { if (isVisible.let { it != null && it }) visible() else gone() }

    @JvmStatic
    @BindingAdapter("displayPoster", "displayBackdrop", "displayLogo", requireAll = false)
    fun ImageView.displayImage(poster: String?, backdrop: String?, logo: String?) {
        when {
            poster != null -> loadImageWithCoil(this, "${Constant.Size.POSTER}$poster")
            backdrop != null -> loadImageWithCoil(this, "${Constant.Size.BACKDROP}$backdrop")
            logo != null -> loadImageWithCoil(this, "${Constant.Size.LOGO}$logo")

            else -> loadImageWithCoil(this, null)
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmStatic
    @BindingAdapter("displayGenres", "displayDate", "displayRuntime", requireAll = false)
    fun TextView.displayText(genres: List<BaseEntity.Genre>?, date: String?, runtime: Int?) {
        when {

            genres != null -> {

                val result = StringBuilder()

                for (i in genres.indices) {

                    result.append(genres[i].name)
                    if (i < genres.size - 1) result.append(", ")

                }

                text = result.toString()

            }

            date != null -> {

                val format = SimpleDateFormat("yyyy-MM-d", Locale.getDefault()).parse(date)
                text = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(format)

            }

            runtime != null -> {

                val hours = runtime / 60
                val minutes = runtime % 60

                text = "$hours ${context.getString(R.string.hours)} $minutes ${context.getString(R.string.minutes)}"

            }

            else -> text = context.getString(R.string.unknown)

        }
    }

    private fun loadImageWithCoil(imageView: ImageView, url: String?) {
        imageView.load("${Constant.BaseUrl.TMDb.IMAGE}$url") {
            placeholder(R.drawable.ic_no_image)
            error(R.drawable.ic_no_image)
            build()
        }
    }

}