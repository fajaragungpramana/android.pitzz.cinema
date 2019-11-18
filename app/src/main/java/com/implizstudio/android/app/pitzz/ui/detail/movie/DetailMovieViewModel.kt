package com.implizstudio.android.app.pitzz.ui.detail.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import com.implizstudio.android.app.pitzz.extension.db
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.runOnUiThread
import org.jetbrains.anko.toast

class DetailMovieViewModel(private val tmDbRepository: TMDbRepository, application: Application) : AndroidViewModel(application) {

    private var queryLanguage = Constant.Key.LANGUAGE_ENGLISH

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(id: Int?): LiveData<Boolean> {
        loadMovie(id)
        return _refreshLayout
    }

    private val _movieFavorite = MutableLiveData<Boolean>()
    fun isMovieFavorite(id: Int?): LiveData<Boolean> {

        GlobalScope.launch {

            val result = getApplication<Application>().db?.getMovieDao()?.onFavoriteMovie(id)
            _movieFavorite.postValue(result != null)

        }

        return _movieFavorite
    }

    private var _movie: MutableLiveData<BaseEntity.DetailMovie>? = null
    fun getMovie(id: Int?): LiveData<BaseEntity.DetailMovie>? {

        if (_movie == null) {
            _movie = MutableLiveData()

            _visibilityProgressBar.value = true
            loadMovie(id)
        }

        return _movie
    }

    private fun loadMovie(id: Int?) {
        GlobalScope.launch {

            when (val response = tmDbRepository.getDetailMovie(id, queryLanguage)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    _movie?.postValue(response.body)
                }

                is ApiResponse.OnFailure -> {
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                }

                is ApiResponse.OnError -> {
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    response.error.printStackTrace()
                }

            }

        }
    }

    fun favoriteListener(movie: BaseEntity.Movie) {

        getApplication<Application>().apply {

            GlobalScope.launch {

                if (_movieFavorite.value.let { it != null && it }) {

                    _movieFavorite.postValue(false)
                    db?.getMovieDao()?.removeFavoriteMovie(movie.id)

                    runOnUiThread { toast(getString(R.string.remove_from_favorites)) }

                } else {

                    _movieFavorite.postValue(true)
                    db?.getMovieDao()?.addFavoriteMovie(movie)

                    runOnUiThread { toast(getString(R.string.added_to_favorites)) }

                }

            }

        }

    }

    fun setQueryLanguage(language: String) { queryLanguage = language }

}