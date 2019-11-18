package com.implizstudio.android.app.pitzz.base

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.util.LanguageUtil

/**
 * @param<B> - Mean layout binding
 */
abstract class BaseActivity<B: ViewDataBinding> : AppCompatActivity() {

    private lateinit var binding: B

    protected abstract fun getContentView(): Int
    protected abstract fun onCreated(savedInstanceState: Bundle?)

    protected fun getLanguagePreference(): SharedPreferences = getSharedPreferences(Constant.Tag.LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)

    override fun attachBaseContext(newBase: Context?) {
        LanguageUtil.onAttach(newBase)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LanguageUtil.onAttach(this)
        binding = DataBindingUtil.setContentView<B>(this, getContentView()).apply { lifecycleOwner = this@BaseActivity }

        onLanguage()

        onCreated(savedInstanceState)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        onLanguage()
        return super.onCreateOptionsMenu(menu)
    }

    protected open fun onEnglishLanguage() { LanguageUtil.setLocale(this, Constant.Key.LANGUAGE_ENGLISH) }

    protected open fun onIndonesiaLanguage() { LanguageUtil.setLocale(this, Constant.Key.LANGUAGE_INDONESIA) }

    protected fun getBinding() = binding

    private fun onLanguage() {

        when (getLanguagePreference().getString(Constant.Key.LANGUAGE, Constant.Key.LANGUAGE_ENGLISH)) {

            Constant.Key.LANGUAGE_ENGLISH -> onEnglishLanguage()

            Constant.Key.LANGUAGE_INDONESIA -> onIndonesiaLanguage()

        }

    }

}