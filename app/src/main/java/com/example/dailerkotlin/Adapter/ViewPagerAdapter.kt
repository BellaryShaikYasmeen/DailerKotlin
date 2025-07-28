package com.example.dailerkotlin.Adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: FragmentActivity, val fragments: List<Fragment>) : FragmentStateAdapter(activity) {
    override fun getItemCount() = fragments.size
    override fun createFragment(position: Int) = fragments[position]
}