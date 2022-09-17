package com.app.kerja_mudah.ui.home.widget.tab

import android.os.Bundle
import android.view.View
import com.app.kerja_mudah.base.BaseFragment
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.data.response.job.JobCategoryResponse
import com.app.kerja_mudah.data.room.job.JobRoomRepository
import com.app.kerja_mudah.databinding.FragmentServiceTabBinding
import com.app.kerja_mudah.di.component.HomeComponent
import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.home.adapter.JobCategoryAdapter
import com.app.kerja_mudah.ui.home.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ServiceTabFragment : BaseFragment<FragmentServiceTabBinding>(FragmentServiceTabBinding::inflate) {

    companion object {}

    @Inject
    lateinit var viewModel:HomeViewModel

    @Inject
    lateinit var jobRoomRepository: JobRoomRepository

    override fun setup(savedInstanceState: Bundle?) {
        initData()
        initAdapter()
        initObserver()
    }

    private var allServiceJob : ArrayList<JobCategoryResponse> =  arrayListOf()
    private fun initData() {
        GlobalScope.launch(Dispatchers.IO) {
            allServiceJob.clear()
            allServiceJob.addAll(jobRoomRepository.getAllServicesJob()?: arrayListOf())
        }
    }

    private fun initObserver() {
        viewModel.homeState.observe(this){
            if (it.jobCategoryState == BaseState.SUCCESS){
                binding?.llShimmer?.visibility = View.GONE
                binding?.rv?.visibility = View.VISIBLE
                allServiceJob.clear()
                allServiceJob.addAll(it.dataJobCategory?.filter { it.flag == "individu" } ?: arrayListOf())
                adapter.notifyDataSetChanged()
            }else if (it.jobCategoryState == BaseState.LOADING){
                if (allServiceJob.isNullOrEmpty()){
                    binding?.llShimmer?.visibility = View.VISIBLE
                    binding?.rv?.visibility = View.GONE
                }else{
                    binding?.llShimmer?.visibility = View.GONE
                    binding?.rv?.visibility = View.VISIBLE
                }
            }else if (it.jobCategoryState == BaseState.FAILED){
                (activity as HomeActivity).showSnackBar(it.errorMessageJobCategory)
            }
        }
    }

    private lateinit var adapter:JobCategoryAdapter
    private fun initAdapter() {
        adapter = JobCategoryAdapter(allServiceJob)
        binding?.rv?.adapter = adapter
    }

    private lateinit var component: HomeComponent
    override fun inject() {
        component = appComponent.homeComponent().create()
        component.inject(this)
    }
}