package com.app.kerja_mudah.data.model.quran

import android.os.Parcelable
import com.app.kerja_mudah.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class QuranFontSize(
    var arabicStyle:FontSize = FontSize.REGULAR,
    var latinSize:FontSize = FontSize.REGULAR,
    var indonesianStyle:FontSize = FontSize.REGULAR
) : Parcelable
enum class FontSize{
    SMALL, REGULAR, LARGE
}