package com.dguitarclassic.githubuserapps2.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dguitarclassic.githubuserapps2.activities.fragment.FollowersFragment
import com.dguitarclassic.githubuserapps2.activities.fragment.FollowingFragment
import com.dguitarclassic.githubuserapps2.R

class  PagerAdapter (private val contentx : Context, fragment : FragmentManager, data: Bundle): FragmentPagerAdapter(fragment, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var bundleFragment : Bundle

    init{
        bundleFragment = data
    }

    @StringRes
    private val TAB = intArrayOf(R.string.tab_1, R.string.tab_2)
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        var fragment : Fragment? = null

        when(position){
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.bundleFragment
        return fragment as Fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return contentx.resources.getString(TAB[position])
    }

}
