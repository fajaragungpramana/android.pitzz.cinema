package com.implizstudio.android.app.pitzz.util

import android.content.Context
import android.preference.PreferenceManager
import androidx.core.content.edit
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import java.util.*

object LanguageUtil {

    fun onAttach(context: Context?): Context? {
        val lang = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context?, defaultLang: String?): Context? {
        val lang = getPersistedData(context, defaultLang)
        return setLocale(context, lang)
    }

    fun setLocale(context: Context?, lang: String?): Context? {
        persist(context, lang)
        return updateResourceLegacy(context, lang)
    }

    @Suppress("DEPRECATION")
    private fun updateResourceLegacy(context: Context?, lang: String?): Context? {
        val locale = Locale(lang)
        Locale.setDefault(locale)

        val res = context?.resources

        val config = res?.configuration
        config?.locale = locale

        res?.updateConfiguration(config, res.displayMetrics)

        return context
    }

    private fun getPersistedData(context: Context?, lang: String?): String? {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        return pref.getString(Constant.Key.LANGUAGE, lang)
    }

    private fun persist(context: Context?, lang: String?) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        pref.edit { putString(Constant.Key.LANGUAGE, lang) }
    }

}