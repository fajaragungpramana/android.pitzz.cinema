package com.implizstudio.android.app.pitzz.di

import android.app.Application
import android.content.Context
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.di.module.appModule
import com.implizstudio.android.app.pitzz.util.LanguageUtil
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LanguageUtil.onAttach(base, Constant.Key.LANGUAGE_ENGLISH))
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(appModule)
        }

    }

}