package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.ui.quran.ListSurahActivity
import com.app.kerja_mudah.ui.quran.SurahDetailActivity
import com.app.kerja_mudah.ui.quran.TafsirSurahActivity
import dagger.Subcomponent

@Subcomponent
interface QuranComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():QuranComponent
    }

    fun inject(activity:ListSurahActivity)
    fun inject(activity:SurahDetailActivity)
    fun inject(activity:TafsirSurahActivity)

}