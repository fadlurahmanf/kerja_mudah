package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.job.JobDetailActivity
import dagger.Component
import dagger.Subcomponent

@Subcomponent
interface JobComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():JobComponent
    }

    fun inject(activity: JobDetailActivity)
}