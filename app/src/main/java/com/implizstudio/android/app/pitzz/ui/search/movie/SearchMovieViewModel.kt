package com.implizstudio.android.app.pitzz.ui.search.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchMovieViewModel(private val tmDbRepository: TMDbRepository) : ViewModel() {

    private val listMovie = mutableListOf<BaseEntity.Movie>()

    private var maxItem = 0
    private var lastVisibleItem = 0

    private var queryPage = 1
    private var queryLanguage = Constant.Key.LANGUAGE_ENGLISH
    private var queryTitle: String? = null

    private val _visibilityShimmer = MutableLiveData<Boolean>()
    fun isVisibleShimmer() = _visibilityShimmer as LiveData<Boolean>

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _visibilityNoMovie = MutableLiveData<Boolean>()
    fun isVisibleNoMovie() = _visibilityNoMovie as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(): LiveData<Boolean> {
        restore()
        loadMovies()

        return _refreshLayout
    }

    private var _movies: MutableLiveData<List<BaseEntity.Movie>>? = null
    fun getMovies(title: String?): LiveData<List<BaseEntity.Movie>>? {
        queryTitle = title

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

    fun searchMovie(title: String?) {
        queryTitle = title
        _visibilityProgressBar.value = true

        restore()
        loadMovies()
    }

    private fun loadMovies() {
        _visibilityNoMovie.value = false

        val map = mutableMapOf(
            "page" to queryPage,
            "language" to queryLanguage,
            "query" to queryTitle
        )

        GlobalScope.launch {

            when (val response = tmDbRepository.searchMovies(map)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)

                    queryPage += 1
                    maxItem += 19

                    _movies?.postValue(listMovie.apply { response.body.let { if (it != null) addAll(it.movieResults) } })

                    if (listMovie.isEmpty()) _visibilityNoMovie.postValue(true)
                }

                is ApiResponse.OnFailure -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    if (listMovie.isEmpty()) _visibilityNoMovie.postValue(true)
                }

                is ApiResponse.OnError -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    _visibilityNoMovie.postValue(false)

                    response.error.printStackTrace()
                }

            }

        }

    }

    private fun restore() {
        maxItem = 0
        lastVisibleItem = 0
        queryPage = 1

        _movies?.value = listMovie.apply { clear() }
    }

}