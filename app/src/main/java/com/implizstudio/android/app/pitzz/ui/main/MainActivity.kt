package com.implizstudio.android.app.pitzz.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.implizstudio.android.app.pitzz.R
import com.implizstudio.android.app.pitzz.base.BaseActivity
import com.implizstudio.android.app.pitzz.databinding.ActivityMainBinding
import com.implizstudio.android.app.pitzz.ui.setting.SettingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.support_toolbar.*
import org.jetbrains.anko.startActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getContentView() = R.layout.activity_main

    override fun onCreated(savedInstanceState: Bundle?) {

        setSupportActionBar(support_toolbar)
        bnv_main.setupWithNavController(Navigation.findNavController(this, R.id.f_main))

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_settings) startActivity<SettingActivity>()

        return super.onOptionsItemSelected(item)
    }

}