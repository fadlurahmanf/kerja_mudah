package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.home.HomeActivity
import com.app.kerja_mudah.ui.home.widget.tab.HomeTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.MyProfileTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.SearchingTabFragment
import com.app.kerja_mudah.ui.home.widget.tab.ServiceTabFragment
import dagger.Subcomponent

@Subcomponent
interface HomeComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():HomeComponent
    }

    fun inject(activity:HomeActivity)
    fun inject(fragment:HomeTabFragment)
    fun inject(fragment:SearchingTabFragment)
    fun inject(fragment:ServiceTabFragment)
    fun inject(fragment:MyProfileTabFragment)
}