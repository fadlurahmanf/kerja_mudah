package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.landing_page.LandingPageActivity
import dagger.Subcomponent


@Subcomponent
interface LandingPageComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():LandingPageComponent
    }

    fun inject(activity:LandingPageActivity)
}