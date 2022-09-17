package com.app.kerja_mudah.ui.core

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.response.freelancer.FreelancerReelResponse
import com.app.kerja_mudah.databinding.ActivityVpverticalVideoBinding
import com.app.kerja_mudah.ui.core.adapter.VPVerticalVideoAdapter

class VPVerticalVideoActivity : BaseActivity<ActivityVpverticalVideoBinding>(ActivityVpverticalVideoBinding::inflate) {
    companion object{
        val TAG = VPVerticalVideoActivity::class.java.simpleName

        const val LIST_VIDEO = "LIST_VIDEO"
    }

    override fun initSetup() {
        list.clear()
        list = intent.getParcelableArrayListExtra<FreelancerReelResponse>(LIST_VIDEO) ?: arrayListOf()
        initAdapter()
    }

    private lateinit var adapter:VPVerticalVideoAdapter
    private var list:ArrayList<FreelancerReelResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = VPVerticalVideoAdapter(this.supportFragmentManager, this.lifecycle)
        adapter.setList(list)
        binding?.vp?.adapter = adapter
    }

    override fun inject() {

    }
}