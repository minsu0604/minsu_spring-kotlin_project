package com.example.myapplication.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.fragments.FavoriteFragment
import com.example.myapplication.fragments.HomeFragment
import com.example.myapplication.fragments.MyFragment
import com.example.myapplication.fragments.SearchFragment

class AdapterForMain(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HomeFragment()
            1 -> SearchFragment()
            2 -> FavoriteFragment()
            else -> MyFragment()
        }
    }
}