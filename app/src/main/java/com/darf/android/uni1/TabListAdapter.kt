package com.darf.android.uni1

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class TabListAdapter(private val fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = fragment.resources.getStringArray(R.array.weekdays).size

    override fun createFragment(position: Int): Fragment {
        return TabFragment(position)
    }
}