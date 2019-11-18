package com.implizstudio.android.app.pitzz.ui.search.television

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchTelevisionViewModel(private val tmDbRepository: TMDbRepository) : ViewModel() {

    private val listTelevision = mutableListOf<BaseEntity.Television>()

    private var maxItem = 0
    private var lastVisibleItem = 0

    private var queryPage = 1
    private var queryLanguage = Constant.Key.LANGUAGE_ENGLISH
    private var queryTitle: String? = null

    private val _visibilityShimmer = MutableLiveData<Boolean>()
    fun isVisibleShimmer() = _visibilityShimmer as LiveData<Boolean>

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _visibilityNoTelevision = MutableLiveData<Boolean>()
    fun isVisibleNoTelevision() = _visibilityNoTelevision as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(): LiveData<Boolean> {
        restore()
        loadTelevisions()

        return _refreshLayout
    }

    private var _televisions: MutableLiveData<List<BaseEntity.Television>>? = null
    fun getTelevisions(title: String?): LiveData<List<BaseEntity.Television>>? {
        queryTitle = title

        if (_televisions == null) {
            _televisions = MutableLiveData()

            _visibilityShimmer.value = true
            loadTelevisions()
        }

        return _televisions
    }

    fun onLastVisibleItemReached(position: Int) {
        lastVisibleItem = position

        if (position == maxItem) {
            _visibilityProgressBar.value = true
            loadTelevisions()
        }
    }

    fun getLastVisibleItem() = lastVisibleItem

    fun searchTelevision(title: String?) {
        queryTitle = title
        _visibilityProgressBar.value = true

        restore()
        loadTelevisions()
    }

    private fun loadTelevisions() {
        _visibilityNoTelevision.value = false

        val map = mutableMapOf(
            "page" to queryPage,
            "language" to queryLanguage,
            "query" to queryTitle
        )

        GlobalScope.launch {

            when (val response = tmDbRepository.searchTelevisions(map)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)

                    queryPage += 1
                    maxItem += 19

                    _televisions?.postValue(listTelevision.apply { response.body.let { if (it != null) addAll(it.televisionResults) } })

                    if (listTelevision.isEmpty()) _visibilityNoTelevision.postValue(true)
                }

                is ApiResponse.OnFailure -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    if (listTelevision.isEmpty()) _visibilityNoTelevision.postValue(true)
                }

                is ApiResponse.OnError -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    _visibilityNoTelevision.postValue(false)

                    response.error.printStackTrace()
                }

            }

        }

    }

    private fun restore() {
        maxItem = 0
        lastVisibleItem = 0
        queryPage = 1

        _televisions?.value = listTelevision.apply { clear() }
    }

}