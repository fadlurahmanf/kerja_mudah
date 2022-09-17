package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.freelancer.*
import dagger.Subcomponent

@Subcomponent
interface FreelancerComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():FreelancerComponent
    }

    fun inject(activity:DetailFreelancerActivity)
    fun inject(activity:FreelancerReviewActivity)
    fun inject(activity:PagingFreelancerActivity)
    fun inject(activity:SelectFreelancerCategoryActivity)
    fun inject(activity:FreelancerDetailFormActivity)
    fun inject(activity:UploadDocumentFreelancerActivity)
    fun inject(activity:RegisterFreelancerSummaryActivity)
    fun inject(activity:RegisterFreelancerLoadingActivity)
}