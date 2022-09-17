package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.example.activity.ExampleActivity
import dagger.Subcomponent

@Subcomponent
interface ExampleComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():ExampleComponent
    }

    fun inject(activity: ExampleActivity)
}