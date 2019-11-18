package com.implizstudio.android.app.pitzz.ui.tab.movie

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.extension.db
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteMovieViewModel(application: Application) : AndroidViewModel(application) {

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _visibilityNoMovie = MutableLiveData<Boolean>()
    fun isVisibleNoMovie() = _visibilityNoMovie as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(): LiveData<Boolean> {
        getMovies()

        return _refreshLayout
    }

    private val _movies = MutableLiveData<List<BaseEntity.Movie>>()
    fun getMovies(): LiveData<List<BaseEntity.Movie>> {

        _visibilityProgressBar.value = true
        GlobalScope.launch {

            val results = getApplication<Application>().db?.getMovieDao()?.getFavoriteMovies()
            if (results.let { it != null && it.isNotEmpty() }) {
                _visibilityProgressBar.postValue(false)
                _visibilityNoMovie.postValue(false)
                _refreshLayout.postValue(false)

                _movies.postValue(results)
            } else {
                _visibilityProgressBar.postValue(false)
                _visibilityNoMovie.postValue(true)
                _refreshLayout.postValue(false)

                _movies.postValue(null)
            }

        }

        return _movies
    }

}