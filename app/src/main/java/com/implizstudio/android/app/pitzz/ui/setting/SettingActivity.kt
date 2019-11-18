package com.implizstudio.android.app.pitzz.ui.setting

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseActivity
import com.implizstudio.android.app.pitzz.data.model.constant.Constant
import com.implizstudio.android.app.pitzz.databinding.ActivitySettingBinding
import com.implizstudio.android.app.pitzz.ui.main.MainActivity
import com.implizstudio.android.app.pitzz.ui.sheet.LanguageSheet
import com.implizstudio.android.app.pitzz.util.NotificationUtil
import kotlinx.android.synthetic.main.activity_setting.*
import kotlinx.android.synthetic.main.support_toolbar.*
import org.jetbrains.anko.startActivity

class SettingActivity : BaseActivity<ActivitySettingBinding>() {

    private var menuItem: Menu? = null

    override fun getContentView() = R.layout.activity_setting

    override fun onCreated(savedInstanceState: Bundle?) {

        val settingPreference = getSharedPreferences(Constant.Tag.SETTING_PREFERENCE, Context.MODE_PRIVATE)
        val notificationUtil = NotificationUtil(this)

        setSupportActionBar(support_toolbar)
        supportActionBar?.let {
            it.title = getString(R.string.settings)
            it.setDisplayHomeAsUpEnabled(true)
        }

        if (settingPreference.getBoolean(Constant.Key.SETTING_RELEASE, false)) s_release_reminder.isChecked = true
        if (settingPreference.getBoolean(Constant.Key.SETTING_DAILY, false)) s_daily_reminder.isChecked = true

        s_release_reminder.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                notificationUtil.setRepeatingNotification(Constant.Key.TYPE_RELEASE, Constant.Tag.ID_RELEASE)
                settingPreference.edit { putBoolean(Constant.Key.SETTING_RELEASE, true) }

            } else {

                notificationUtil.cancelNotification(Constant.Key.TYPE_RELEASE)
                settingPreference.edit { putBoolean(Constant.Key.SETTING_RELEASE, false) }

            }

        }

        s_daily_reminder.setOnCheckedChangeListener { _, isChecked ->

            if (isChecked) {

                notificationUtil.setRepeatingNotification(Constant.Key.TYPE_DAILY, Constant.Tag.ID_DAILY)
                settingPreference.edit { putBoolean(Constant.Key.SETTING_DAILY, true) }

            } else {

                notificationUtil.cancelNotification(Constant.Key.TYPE_DAILY)
                settingPreference.edit { putBoolean(Constant.Key.SETTING_DAILY, false) }

            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuItem = menu
        menuInflater.inflate(R.menu.toolbar_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId == R.id.action_flag) {

            LanguageSheet(object : LanguageSheet.Choose {

                override fun isEnglish() {

                    onEnglishLanguage()
                    getLanguagePreference().edit { putString(Constant.Key.LANGUAGE, Constant.Key.LANGUAGE_ENGLISH) }

                }

                override fun isIndonesia() {

                    onIndonesiaLanguage()
                    getLanguagePreference().edit { putString(Constant.Key.LANGUAGE, Constant.Key.LANGUAGE_INDONESIA) }

                }

            }).show(supportFragmentManager, Constant.Tag.LANGUAGE_SHEET)


        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        startActivity<MainActivity>()
        finish()
    }

    override fun onEnglishLanguage() {
        super.onEnglishLanguage()

        setIconFlag(R.drawable.ic_flag_english)
    }

    override fun onIndonesiaLanguage() {
        super.onIndonesiaLanguage()

        setIconFlag(R.drawable.ic_flag_indonesia)
    }

    private fun setIconFlag(res: Int) { menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, res) }

}