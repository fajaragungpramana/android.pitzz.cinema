package com.implizstudio.android.app.pitzz.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

@Suppress("DEPRECATION")
class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val listFragment = mutableListOf<Fragment>()
    private val listTitle = mutableListOf<String>()

    override fun getCount() = listFragment.size

    override fun getItem(position: Int) = listFragment[position]

    override fun getPageTitle(position: Int) = listTitle[position]

    fun setup(fragment: Fragment, title: String) {
        listFragment.add(fragment)
        listTitle.add(title)
    }

}