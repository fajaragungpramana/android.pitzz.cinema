package com.implizstudio.android.app.pitzz.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.implizstudio.android.app.pitzz.data.model.constant.Constant

/**
 * @param<B> - Mean layout binding
 */
abstract class BaseFragment<B: ViewDataBinding> : Fragment() {

    private lateinit var binding: B

    protected abstract fun getContentView(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate<B>(inflater, getContentView(), container, false).apply { lifecycleOwner = this@BaseFragment }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        val languagePreference = activity?.getSharedPreferences(Constant.Tag.LANGUAGE_PREFERENCE, Context.MODE_PRIVATE)
        when (languagePreference?.getString(Constant.Key.LANGUAGE, Constant.Key.LANGUAGE_ENGLISH)) {

            Constant.Key.LANGUAGE_ENGLISH -> onEnglishLanguage()

            Constant.Key.LANGUAGE_INDONESIA -> onIndonesiaLanguage()

        }

    }

    protected abstract fun onEnglishLanguage()

    protected abstract fun onIndonesiaLanguage()

    protected fun getBinding() = binding

}