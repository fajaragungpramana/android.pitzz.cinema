package com.implizstudio.android.app.pitzz.ui.detail.television

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

class DetailTelevisionViewModel(private val tmDbRepository: TMDbRepository, application: Application) : AndroidViewModel(application) {

    private var queryLanguage = Constant.Key.LANGUAGE_ENGLISH

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(id: Int?): LiveData<Boolean> {
        loadTelevision(id)
        return _refreshLayout
    }

    private val _televisionFavorite = MutableLiveData<Boolean>()
    fun isTelevisionFavorite(id: Int?): LiveData<Boolean> {

        GlobalScope.launch {

            val result = getApplication<Application>().db?.getTelevisionDao()?.onFavoriteTelevision(id)
            _televisionFavorite.postValue(result != null)

        }

        return _televisionFavorite
    }

    private var _television: MutableLiveData<BaseEntity.DetailMovie>? = null
    fun getTelevision(id: Int?): LiveData<BaseEntity.DetailMovie>? {

        if (_television == null) {
            _television = MutableLiveData()

            _visibilityProgressBar.value = true
            loadTelevision(id)
        }

        return _television
    }

    private fun loadTelevision(id: Int?) {

        GlobalScope.launch {

            when (val response = tmDbRepository.getDetailTelevision(id, queryLanguage)) {

                is ApiResponse.OnSuccess -> {
                    _visibilityProgressBar.postValue(false)
                    _refreshLayout.postValue(false)
                    _television?.postValue(response.body)
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

    fun favoriteListener(television: BaseEntity.Television) {

        getApplication<Application>().apply {

            GlobalScope.launch {

                if (_televisionFavorite.value.let { it != null && it }) {

                    _televisionFavorite.postValue(false)
                    db?.getTelevisionDao()?.removeFavoriteTelevision(television.id)

                    runOnUiThread { toast(getString(R.string.remove_from_favorites)) }

                } else {

                    _televisionFavorite.postValue(true)
                    db?.getTelevisionDao()?.addFavoriteTelevision(television)

                    runOnUiThread { toast(getString(R.string.added_to_favorites)) }

                }

            }

        }

    }

    fun setQueryLanguage(language: String) { queryLanguage = language }

}