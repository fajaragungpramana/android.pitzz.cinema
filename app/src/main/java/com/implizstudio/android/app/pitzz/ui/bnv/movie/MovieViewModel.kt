package com.implizstudio.android.app.pitzz.ui.bnv.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MovieViewModel(private val tmDbRepository: TMDbRepository) : ViewModel() {

    private val listMovie = mutableListOf<BaseEntity.Movie>()

    private var queryPage = 1
    private var queryLanguage = Constant.Key.LANGUAGE_ENGLISH

    private var maxItem = 0
    private var lastVisibleItem = 0

    private val _visibilityShimmer = MutableLiveData<Boolean>()
    fun isVisibleShimmer() = _visibilityShimmer as LiveData<Boolean>

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(): LiveData<Boolean> {
        queryPage = 1
        maxItem = 0
        lastVisibleItem = 0

        _refreshLayout.value = true
        _movies?.value = listMovie.apply { clear() }

        loadMovies()

        return _refreshLayout
    }

    private var _movies: MutableLiveData<List<BaseEntity.Movie>>? = null
    fun getMovies(): LiveData<List<BaseEntity.Movie>>? {

        if (_movies == null) {
            _movies = MutableLiveData()

            _visibilityShimmer.value = true
            loadMovies()
        }

        return _movies
    }

    fun onLastVisibleItemReached(position: Int) {
        lastVisibleItem = position

        if (position == maxItem) {
            _visibilityProgressBar.value = true
            loadMovies()
        }
    }

    fun getLastVisibleItem() = lastVisibleItem

    private fun loadMovies() {

        val map = mutableMapOf<String, Any?>(
            "page" to queryPage,
            "language" to queryLanguage
        )

        GlobalScope.launch {

            when (val response = tmDbRepository.getMovies(map)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)

                    queryPage += 1
                    maxItem += 19

                    _movies?.postValue(listMovie.apply { response.body.let { if (it != null) addAll(it.movieResults) } })
                }

                is ApiResponse.OnFailure -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                }

                is ApiResponse.OnError -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)

                    response.error.printStackTrace()
                }

            }

        }

    }

    fun setQueryLanguage(language: String) { queryLanguage = language }

}