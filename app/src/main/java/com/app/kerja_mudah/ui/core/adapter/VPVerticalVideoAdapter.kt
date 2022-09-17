package com.app.kerja_mudah.ui.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.ui.core.widget.ReelFragment

class VPVerticalVideoAdapter(fm:FragmentManager, activity:Lifecycle):FragmentStateAdapter(fm, activity) {
    private var list:ArrayList<FreelancerReelResponse> = arrayListOf()

    fun setList(list:ArrayList<FreelancerReelResponse>){
        this.list = list
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ReelFragment.newInstance(list[position])
    }

}