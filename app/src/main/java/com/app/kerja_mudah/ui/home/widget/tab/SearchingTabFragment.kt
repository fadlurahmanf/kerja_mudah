package com.app.kerja_mudah.ui.home.widget.tab

import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.app.kerja_mudah.base.BaseFragment
import com.app.kerja_mudah.core.extension.setLightStatusBarColor
import com.app.kerja_mudah.databinding.FragmentSearchingTabBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.home.adapter.SearchingTabAdapter
import com.app.kerja_mudah.ui.home.viewmodel.HomeViewModel
import com.google.android.material.tabs.TabLayoutMediator
import javax.inject.Inject

class SearchingTabFragment : BaseFragment<FragmentSearchingTabBinding>(FragmentSearchingTabBinding::inflate) {

    @Inject
    lateinit var viewModel:HomeViewModel

    companion object {}

    override fun setup(savedInstanceState: Bundle?) {
        this.requireActivity().setLightStatusBarColor(true)
        initAdapter()
        initAction()
        viewModel.getAllJobCategory()
        initObserver()
    }

    private fun initAction() {
        binding?.btnLogin?.setOnClickListener {
            (requireActivity() as HomeActivity).showRequiredLoginDialog()
        }
    }

    private fun initObserver() {}

    private lateinit var searchingTabAdapter:SearchingTabAdapter
    private fun initAdapter() {
        searchingTabAdapter = SearchingTabAdapter(activity?.supportFragmentManager!!, activity?.lifecycle!!)
        binding?.viewPager2?.getChildAt(0)?.overScrollMode = ViewPager2.OVER_SCROLL_NEVER
        binding?.viewPager2?.adapter = searchingTabAdapter
        TabLayoutMediator(binding!!.tabLayout, binding!!.viewPager2){ tab, position ->
            if (position == 0){
                tab.text = "Service"
            }else{
                tab.text = "Work"
            }
        }.attach()
    }

    private lateinit var homeComponent: HomeComponent
    override fun inject() {
        homeComponent = appComponent.homeComponent().create()
        homeComponent.inject(this)
    }
}