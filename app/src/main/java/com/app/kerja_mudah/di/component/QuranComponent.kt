package com.app.kerja_mudah.di.component

import com.app.kerja_mudah.databinding.BottomsheetChooseQuranFontSizeBinding
import com.app.kerja_mudah.ui.quran.ListSurahActivity
import com.app.kerja_mudah.ui.quran.SurahDetailActivity
import com.app.kerja_mudah.ui.quran.TafsirSurahActivity
import com.app.kerja_mudah.ui.quran.widget.ChooseQuranFontSizeBottomSheet
import dagger.Subcomponent

@Subcomponent
interface QuranComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():QuranComponent
    }

    fun inject(bottomSheet:ChooseQuranFontSizeBottomSheet)

    fun inject(activity:ListSurahActivity)
    fun inject(activity:SurahDetailActivity)
    fun inject(activity:TafsirSurahActivity)

}