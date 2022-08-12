package com.test.italika.ui.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.test.italika.ui.fragments.MovieFragment

// Fragment controlling system
class SectionPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    companion object {
        val TAB_TITLES:List<String> = listOf("Popular","Top Rated","Upcoming")
        val TAB_NET:List<String> = listOf("popular","top_rated","upcoming")
        const val default:String = "popular"
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment.create(TAB_NET[position])
            1 -> MovieFragment.create(TAB_NET[position])
            2 -> MovieFragment.create(TAB_NET[position])
            else -> MovieFragment.create(default)
        }
    }

    override fun getPageTitle(position: Int): CharSequence? =
        TAB_TITLES[position]

    override fun getCount(): Int = TAB_TITLES.size
}