package com.implizstudio.android.app.pitzz.ui.bnv.television

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.data.remote.ApiResponse
import com.implizstudio.android.app.pitzz.data.repository.TMDbRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TelevisionViewModel(private val tmDbRepository: TMDbRepository) : ViewModel() {

    private val listTelevision = mutableListOf<BaseEntity.Television>()

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
        _televisions?.value = listTelevision.apply { clear() }

        loadTelevisions()

        return _refreshLayout
    }

    private var _televisions: MutableLiveData<List<BaseEntity.Television>>? = null
    fun getTelevisions(): LiveData<List<BaseEntity.Television>>? {

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

    private fun loadTelevisions() {

        val map = mutableMapOf<String, Any?>(
            "page" to queryPage,
            "language" to queryLanguage
        )

        GlobalScope.launch {

            when (val response = tmDbRepository.getTelevisions(map)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityShimmer.postValue(false)
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)

                    queryPage += 1
                    maxItem += 19

                    _televisions?.postValue(listTelevision.apply { response.body.let { if (it != null) addAll(it.televisionResults) } })
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