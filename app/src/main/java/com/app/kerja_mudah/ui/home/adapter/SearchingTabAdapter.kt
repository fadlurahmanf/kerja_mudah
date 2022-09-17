package com.app.kerja_mudah.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.kerja_mudah.ui.home.widget.tab.ServiceTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.WorkTabFragment

class SearchingTabAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ServiceTabFragment()
            else -> WorkTabFragment()
        }
    }
}