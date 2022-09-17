package com.app.kerja_mudah.ui.freelancer

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseActivity
import com.app.kerja_mudah.data.model.freelancer.FreelancerCategoryModel
import com.app.kerja_mudah.data.repository.freelancer.FreelancerRepository
import com.app.kerja_mudah.databinding.ActivitySelectFreelancerCategoryBinding
import com.app.kerja_mudah.di.component.FreelancerComponent
import com.app.kerja_mudah.ui.freelancer.adapter.FreelancerCategorySelectorAdapter
import javax.inject.Inject

class SelectFreelancerCategoryActivity : BaseActivity<ActivitySelectFreelancerCategoryBinding>(ActivitySelectFreelancerCategoryBinding::inflate) {

    @Inject
    lateinit var repository: FreelancerRepository

    private var categories:ArrayList<FreelancerCategoryModel> = arrayListOf(
        FreelancerCategoryModel("Photographer", R.drawable.photographer_category),
        FreelancerCategoryModel("Technology", R.drawable.technology_category),
        FreelancerCategoryModel("Architect", R.drawable.architect_category),
        FreelancerCategoryModel("Wedding Organizer", R.drawable.photographer_category),
        FreelancerCategoryModel("Editor Video", R.drawable.technology_category),
        FreelancerCategoryModel("Android Developer", R.drawable.architect_category),
        FreelancerCategoryModel("IOS Developer", R.drawable.architect_category),
    )

    private var selectedCategory:ArrayList<FreelancerCategoryModel> = arrayListOf()

    override fun initSetup() {
        initData()
        initView()
        initAdapter()
        initAction()
    }

    private fun initData() {
        if (repository.freelancerCategory != null){
            selectedCategory.clear()
            selectedCategory.add(repository.freelancerCategory!!)
            refreshButton()
        }
    }

    private fun initAction() {
        binding?.btnNext?.setOnClickListener {
            if (selectedCategory.isNotEmpty()){
                repository.freelancerCategory = selectedCategory.first()
                val intent = Intent(this, FreelancerDetailFormActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initView(){
        binding?.btnNext?.setButtonEnabled(selectedCategory.isNotEmpty())
    }

    private fun refreshButton(){
        binding?.btnNext?.setButtonEnabled(selectedCategory.isNotEmpty())
    }

    private lateinit var adapter: FreelancerCategorySelectorAdapter
    private fun initAdapter() {
        adapter = FreelancerCategorySelectorAdapter(categories, selectedCategory)
        adapter.setCallBack(object : FreelancerCategorySelectorAdapter.CallBack{
            override fun onClicked(model: FreelancerCategoryModel) {
                selectedCategory.clear()
                selectedCategory.add(model)
                adapter.notifyDataSetChanged()
                refreshButton()
            }
        })
        val layoutManager = GridLayoutManager(this, 2)
        binding?.rvCategory?.layoutManager = layoutManager
        binding?.rvCategory?.adapter = adapter
    }

    private lateinit var component:FreelancerComponent
    override fun inject() {
        component = appComponent.freelancerComponent().create()
        component.inject(this)
    }
}