package com.app.kerja_mudah.ui.freelancer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.asLiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.mapper.FreelancerMapper
import com.app.kerja_mudah.databinding.ActivityPagingFreelancerBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.freelancer.adapter.FreelancerPagingAdapter
import com.app.kerja_mudah.ui.freelancer.viewmodel.FreelancerViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import java.util.*
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalPagingApi::class)
class PagingFreelancerActivity : BaseActivity<ActivityPagingFreelancerBinding>(ActivityPagingFreelancerBinding::inflate) {

    companion object{
        val TAG = PagingFreelancerActivity::class.java.simpleName
    }

    @Inject
    lateinit var viewModel:FreelancerViewModel

    @Inject
    lateinit var mapper: FreelancerMapper

    override fun initSetup() {
        initAction()
        initAdapter()
        viewModel.getAllPagingFreelancer().subscribe {
            binding?.swl?.isRefreshing = false
            binding?.rvFreelancer?.visibility = View.VISIBLE
            adapter.submitData(lifecycle, it)
        }

        adapter.addLoadStateListener {
            Log.i(TAG, it.source.toString())

            if (it.mediator?.refresh is LoadState.Loading){
                binding?.pb?.visibility = View.VISIBLE
            }else if (it.mediator?.refresh is LoadState.NotLoading){
                binding?.pb?.visibility = View.GONE
            }

            if (it.mediator?.refresh is LoadState.Error){
                showSnackBar((it.mediator?.refresh as LoadState.Error).error.message)
            }
        }

        binding?.rvFreelancer?.canScrollVertically(-1)


        binding?.rvFreelancer?.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (binding?.rvFreelancer?.canScrollVertically(-1) != true){
                    changeIntoWhiteToolBar()
                    setStatusBarTextColor(true)
                }else{
                    changeIntoGreenToolBar()
                    setStatusBarTextColor(false)
                }
            }
        })
    }

    private fun changeIntoGreenToolBar(){
        binding?.toolbar?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.green)
        binding?.ivBack?.imageTintList = ContextCompat.getColorStateList(this, R.color.white)
        binding?.tvKerjaMudahTitle?.setTextColor(ContextCompat.getColorStateList(this, R.color.white))
        window.statusBarColor = ContextCompat.getColor(this, R.color.green)
    }

    private fun changeIntoWhiteToolBar(){
        binding?.toolbar?.backgroundTintList = ContextCompat.getColorStateList(this, R.color.white)
        binding?.ivBack?.imageTintList = ContextCompat.getColorStateList(this, R.color.green)
        binding?.tvKerjaMudahTitle?.setTextColor(ContextCompat.getColorStateList(this, R.color.green))
        window.statusBarColor = ContextCompat.getColor(this, R.color.white)
    }

    private fun initAction() {
        binding?.swl?.setOnRefreshListener {
            binding?.swl?.isRefreshing = true
            viewModel.getAllPagingFreelancer().subscribe(
                {
//                        binding?.pb?.visibility = View.VISIBLE
//                        binding?.pb?.visibility = View.GONE
                    adapter.submitData(lifecycle, it)
                },
                {
//                        binding?.pb?.visibility = View.VISIBLE
//                        binding?.pb?.visibility = View.GONE
                    showSnackBar(message = it.message)
                }
            )
        }
    }

    private lateinit var adapter:FreelancerPagingAdapter
    private fun initAdapter() {
        adapter = FreelancerPagingAdapter()
        val layoutManager = LinearLayoutManager(this)
        binding?.rvFreelancer?.layoutManager = layoutManager
        binding?.rvFreelancer?.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
    }

    private lateinit var component: FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }

}