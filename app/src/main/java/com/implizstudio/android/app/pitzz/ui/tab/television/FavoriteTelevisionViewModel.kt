package com.implizstudio.android.app.pitzz.ui.tab.television

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.implizstudio.android.app.pitzz.data.model.BaseEntity
import com.implizstudio.android.app.pitzz.extension.db
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FavoriteTelevisionViewModel(application: Application) : AndroidViewModel(application) {

    private val _visibilityProgressBar = MutableLiveData<Boolean>()
    fun isVisibleProgressBar() = _visibilityProgressBar as LiveData<Boolean>

    private val _visibilityNoTelevision = MutableLiveData<Boolean>()
    fun isVisibleNoTelevision() = _visibilityNoTelevision as LiveData<Boolean>

    private val _refreshLayout = MutableLiveData<Boolean>()
    fun isRefreshing(): LiveData<Boolean> {
        getTelevisions()

        return _refreshLayout
    }

    private val _televisions = MutableLiveData<List<BaseEntity.Television>>()
    fun getTelevisions(): LiveData<List<BaseEntity.Television>> {

        _visibilityProgressBar.value = true
        GlobalScope.launch {

            val results = getApplication<Application>().db?.getTelevisionDao()?.getFavoriteTelevisions()
            if (results.let { it != null && it.isNotEmpty() }) {
                _visibilityProgressBar.postValue(false)
                _visibilityNoTelevision.postValue(false)
                _refreshLayout.postValue(false)

                _televisions.postValue(results)
            } else {
                _visibilityProgressBar.postValue(false)
                _visibilityNoTelevision.postValue(true)
                _refreshLayout.postValue(false)

                _televisions.postValue(null)
            }

        }

        return _televisions
    }

}