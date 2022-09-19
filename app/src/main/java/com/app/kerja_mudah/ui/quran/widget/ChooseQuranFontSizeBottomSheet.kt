package com.app.kerja_mudah.ui.quran.widget

import android.content.DialogInterface
import android.os.Build
import android.text.Html
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.app.kerja_mudah.base.BaseBottomSheet
import com.app.kerja_mudah.data.model.quran.FontSize
import com.app.kerja_mudah.data.model.quran.QuranFontSize
import com.app.kerja_mudah.data.repository.quran.QuranRepository
import com.app.kerja_mudah.databinding.BottomsheetChooseQuranFontSizeBinding
import com.app.kerja_mudah.di.component.QuranComponent
import javax.inject.Inject

class ChooseQuranFontSizeBottomSheet:BaseBottomSheet<BottomsheetChooseQuranFontSizeBinding>(BottomsheetChooseQuranFontSizeBinding::inflate) {
    companion object{

    }

    private var callBack:CallBack ?= null

    fun setCallBack(callBack: CallBack){
        this.callBack = callBack
    }

    private var size:QuranFontSize = QuranFontSize()

    private lateinit var component: QuranComponent
    override fun inject() {
        component = appComponent.quranComponent().create()
        component.inject(this)
    }

    override fun setup() {
        size = repository.fontSize
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding?.tvExampleLatin?.text = Html.fromHtml("i<u>dzaa</u> j<u>aa</u>-a na<u>sh</u>ru <strong>al</strong>l<u>aa</u>hi wa<strong>a</strong>lfat<u>h</u><strong>u</strong>", Html.FROM_HTML_MODE_COMPACT)
        }else{
            binding?.tvExampleLatin?.text = Html.fromHtml("i<u>dzaa</u> j<u>aa</u>-a na<u>sh</u>ru <strong>al</strong>l<u>aa</u>hi wa<strong>a</strong>lfat<u>h</u><strong>u</strong>")
        }
        refreshArabicStyle()
        refreshLatinStyle()
        refreshIdnStyle()
        initAction()
    }

    private fun initAction() {
        binding?.llArSmall?.setOnClickListener {
            size.arabicStyle = FontSize.SMALL
            repository.fontSize = size
            refreshArabicStyle()
        }

        binding?.llArRegular?.setOnClickListener {
            size.arabicStyle = FontSize.REGULAR
            repository.fontSize = size
            refreshArabicStyle()
        }

        binding?.llArLarge?.setOnClickListener {
            size.arabicStyle = FontSize.LARGE
            repository.fontSize = size
            refreshArabicStyle()
        }

        binding?.llLatinSmall?.setOnClickListener {
            size.latinSize = FontSize.SMALL
            repository.fontSize = size
            refreshLatinStyle()
        }

        binding?.llLatinRegular?.setOnClickListener {
            size.latinSize = FontSize.REGULAR
            repository.fontSize = size
            refreshLatinStyle()
        }

        binding?.llLatinLarge?.setOnClickListener {
            size.latinSize = FontSize.LARGE
            repository.fontSize = size
            refreshLatinStyle()
        }

        binding?.llIdnSmall?.setOnClickListener {
            size.indonesianStyle = FontSize.SMALL
            repository.fontSize = size
            refreshIdnStyle()
        }

        binding?.llIdnRegular?.setOnClickListener {
            size.indonesianStyle = FontSize.REGULAR
            repository.fontSize = size
            refreshIdnStyle()
        }

        binding?.llIdnLarge?.setOnClickListener {
            size.indonesianStyle = FontSize.LARGE
            repository.fontSize = size
            refreshIdnStyle()
        }
    }

    private fun refreshArabicStyle(){
        binding?.ivArSmall?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.arabicStyle == FontSize.SMALL) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivArRegular?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.arabicStyle == FontSize.REGULAR) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivArLarge?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.arabicStyle == FontSize.LARGE) R.drawable.circle_selected else R.drawable.circle_unselected))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvExampleAr?.setTextAppearance(if (size.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (size.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
        }else{
            binding?.tvExampleAr?.setTextAppearance(requireContext(), if (size.arabicStyle == FontSize.LARGE) R.style.Font_Bold_40 else if (size.arabicStyle == FontSize.SMALL) R.style.Font_Bold_30 else R.style.Font_Bold_35)
        }
    }

    private fun refreshLatinStyle(){
        binding?.ivLatinSmall?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.latinSize == FontSize.SMALL) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivLatinRegular?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.latinSize == FontSize.REGULAR) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivLatinLarge?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.latinSize == FontSize.LARGE) R.drawable.circle_selected else R.drawable.circle_unselected))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvExampleLatin?.setTextAppearance(if (size.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (size.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }else{
            binding?.tvExampleLatin?.setTextAppearance(requireContext(), if (size.latinSize == FontSize.LARGE) R.style.Font_Regular_20 else if (size.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }
    }

    private fun refreshIdnStyle(){
        binding?.ivIdnSmall?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.indonesianStyle == FontSize.SMALL) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivIdnRegular?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.indonesianStyle == FontSize.REGULAR) R.drawable.circle_selected else R.drawable.circle_unselected))
        binding?.ivIdnLarge?.setImageDrawable(ContextCompat.getDrawable(requireContext(), if (size.indonesianStyle == FontSize.LARGE) R.drawable.circle_selected else R.drawable.circle_unselected))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding?.tvExampleIdn?.setTextAppearance(if (size.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (size.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }else{
            binding?.tvExampleIdn?.setTextAppearance(requireContext(), if (size.indonesianStyle == FontSize.LARGE) R.style.Font_Regular_20 else if (size.latinSize == FontSize.SMALL) R.style.Font_Regular_12 else R.style.Font_Regular_16)
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        callBack?.onDismiss()
        super.onDismiss(dialog)
    }

    @Inject
    lateinit var repository:QuranRepository

    interface CallBack{
        fun onDismiss()
    }
}