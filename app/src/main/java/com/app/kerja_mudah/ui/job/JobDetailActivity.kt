package com.app.kerja_mudah.ui.job

import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.base.BaseState
import com.app.kerja_mudah.core.extension.formatDate2
import com.app.kerja_mudah.core.extension.formatDate4
import com.app.kerja_mudah.core.extension.toDate
import com.app.kerja_mudah.data.response.job.JobDetailResponse
import com.app.kerja_mudah.data.response.job.JobResponse
import com.app.kerja_mudah.databinding.ActivityJobDetailBinding
import com.app.kerja_mudah.di.component.JobComponent
import com.app.kerja_mudah.ui.home.adapter.JobAdapter
import com.app.kerja_mudah.ui.job.viewmodel.JobDetailViewModel
import com.bumptech.glide.Glide
import javax.inject.Inject

class JobDetailActivity : BaseActivity<ActivityJobDetailBinding>(ActivityJobDetailBinding::inflate) {
    companion object{
        const val JOB = "JOB_RESPONSE"
    }

    private var jobResponse:JobResponse ?= null
    private var jobDetailResponse:JobDetailResponse ?= null
    private var isCompanyShowMore = false

    override fun initSetup() {
        jobResponse = intent.getParcelableExtra(JOB)

        if (jobResponse?.id == null){
            showOkDialog("Oops...", content = "Job is required", listener = {
                dismissOkDialog()
                finish()
            })
            return
        }

        initAction()
        initAdapter()
        initObserver()

        viewModel.getDetailJob(jobResponse?.id?:-1)
        viewModel.getListJobByCategoryId(jobResponse?.jobCategoryId?:-1)
    }

    private fun initAction() {
        binding?.tvShowMoreCompanyDescription?.setOnClickListener {
            if (isCompanyShowMore){
                isCompanyShowMore = false
                binding?.tvCompanyDescription?.maxLines = 4
            }else{
                isCompanyShowMore = true
                binding?.tvCompanyDescription?.maxLines = 999
            }
        }
    }

    private lateinit var adapter:JobAdapter
    private var list:ArrayList<JobResponse> = arrayListOf()
    private fun initAdapter() {
        adapter = JobAdapter()
        adapter.setCallBack(object : JobAdapter.CallBack{
            override fun onClicked(job: JobResponse) {

            }
        })
        adapter.setList(list)
        binding?.rvMoreJob?.adapter = adapter
    }

    private fun initObserver() {
        viewModel.state.observe(this){
            if (it.getDetailJobState == BaseState.SUCCESS){
                binding?.layoutFailed?.visibility = View.GONE
                binding?.layoutLoading?.visibility = View.GONE
                binding?.layoutSuccess?.visibility = View.VISIBLE

                jobDetailResponse = it.detailJob
                updateDetailJobView()
                if (it.getListJobByCategoryIdState == BaseState.SUCCESS){
                    binding?.llMoreJob?.visibility = View.VISIBLE
                    updateMoreJob(it.listJobByCategoryId?: listOf())
                }
            }else if (it.getDetailJobState == BaseState.LOADING){
                binding?.layoutFailed?.visibility = View.GONE
                binding?.layoutLoading?.visibility = View.VISIBLE
                binding?.layoutSuccess?.visibility = View.GONE
            }else if (it.getDetailJobState == BaseState.FAILED){
                binding?.layoutFailed?.visibility = View.VISIBLE
                binding?.layoutLoading?.visibility = View.GONE
                binding?.layoutSuccess?.visibility = View.GONE
            }

            if (it.getListJobByCategoryIdState == BaseState.SUCCESS){
                binding?.llMoreJob?.visibility = View.VISIBLE
                updateMoreJob(it.listJobByCategoryId?: listOf())
            }
        }
    }

    private fun updateMoreJob(list:List<JobResponse>) {
        this.list.clear()
        this.list.addAll(list)
        adapter.setList(this.list)
    }

    private fun updateDetailJobView() {
        binding?.tvJobTitleToolbar?.text = jobDetailResponse?.name?:""
        binding?.tvJobTitle?.text = jobDetailResponse?.name?:""

        Glide.with(binding!!.ivCompanyLogo).load(jobDetailResponse?.company?.photo?:"")
            .error(ContextCompat.getDrawable(this, R.drawable.placeholder_broken_image))
            .centerCrop()
            .into(binding!!.ivCompanyLogo)

        binding?.tvCompanyTitle?.text = jobDetailResponse?.company?.name?:""
        binding?.tvCompanyCity?.text = "null"

        binding?.tvJobDetailNotes?.text = "${jobDetailResponse?.createdAt?.toDate()?.formatDate2()} • 0 Applicants"
        binding?.tvWorkHour?.text = if (jobDetailResponse?.fullDay == true){
            "Full Day"
        }else if (jobDetailResponse?.workHour != null){
            "${jobDetailResponse?.workHour?:"0"} Work Hour • ${jobDetailResponse?.workTime?.toDate(onlyTime = true)?.formatDate4()?:""}"
        } else {
            ""
        }
        binding?.tvJobDescription?.text = jobDetailResponse?.description?:""

        val jobDeskViewList = arrayOfNulls<TextView>(jobDetailResponse?.jobDesk?.size?:0)
        binding?.llJobDesk?.removeAllViews()
        for (i in 0 until jobDeskViewList.size-1){
            val view = TextView(this)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.layoutParams = lp
            view.text = "• ${jobDetailResponse?.jobDesk?.get(i)}"
            binding?.llJobDesk?.addView(view)
        }
        val responsibilityViewList = arrayOfNulls<TextView>(jobDetailResponse?.responsibility?.size?:0)
        binding?.llResponsibility?.removeAllViews()
        for (i in 0 until responsibilityViewList.size - 1){
            val view = TextView(this)
            val lp = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            view.layoutParams = lp
            view.text = "• ${jobDetailResponse?.responsibility?.get(i)}"
            binding?.llResponsibility?.addView(view)
        }

        Glide.with(binding!!.ivCompanyLogo2)
            .load(jobDetailResponse?.company?.photo?:"")
            .centerCrop()
            .error(ContextCompat.getDrawable(this, R.drawable.placeholder_broken_image))
            .into(binding!!.ivCompanyLogo2)

        binding?.tvCompanyName2?.text = jobDetailResponse?.company?.name?:""
        binding?.tvCompanyCity2?.text = "null"
        binding?.tvCompanyDescription?.text = jobDetailResponse?.company?.description?:""
    }


    @Inject
    lateinit var viewModel:JobDetailViewModel

    private lateinit var component: JobComponent
    override fun inject() {
        component = appComponent.jobComponent().create()
        component.inject(this)
    }
}