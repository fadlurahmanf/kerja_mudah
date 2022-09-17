package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.service.DetailServiceActivity
import com.app.kerja_mudah.ui.service.ServiceOrderActivity
import dagger.Subcomponent

@Subcomponent
interface ServiceComponent {
    @Subcomponent.Factory
    interface Factory{
        fun create():ServiceComponent
    }

    fun inject(activity:DetailServiceActivity)
    fun inject(activity:ServiceOrderActivity)
}