package com.app.kerja_mudah.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import com.app.kerja_mudah.R
import java.lang.IllegalArgumentException

class KerjaMudahIndicator @JvmOverloads constructor(
    context: Context, attrs: AttributeSet
) : LinearLayout(context, attrs) {
    private var attributes : TypedArray
    private var ll:LinearLayout

    private var totalStep:Int
    private var currentStep:Int

    init {
        inflate(context, R.layout.kerja_mudah_indicator, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.IndicatorKerjaMudah)

        ll = findViewById(R.id.ll)
        totalStep = attributes.getInt(R.styleable.IndicatorKerjaMudah_total_step, 0)
        currentStep = attributes.getInt(R.styleable.IndicatorKerjaMudah_current_step, 0)
        if(currentStep > totalStep) throw IllegalArgumentException("Current step should be lower than total step")
        initView()
    }

    private fun initView() {
        ll.removeAllViews()
        val indicatorList = arrayOfNulls<TextView>(totalStep)
        for (i in indicatorList.indices){
            val view = TextView(context)
            val lp = LinearLayout.LayoutParams(100, 100)
            view.layoutParams = lp
            indicatorList[i] = view
            view.text = "${i+1}"
            view.gravity = Gravity.CENTER
            if (i < currentStep - 1){
                view.background = AppCompatResources.getDrawable(context, R.drawable.green_solid_circle)
            }else if (i == currentStep-1){
                view.background = AppCompatResources.getDrawable(context, R.drawable.solid_white_stroke_2_green_circle)
            }else{
                view.background = AppCompatResources.getDrawable(context, R.drawable.light_grey_circle)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (i < currentStep - 1){
                    view.setTextColor(AppCompatResources.getColorStateList(context, R.color.white))
                }else if (i == currentStep-1){
                    view.setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                }else{
                    view.setTextColor(AppCompatResources.getColorStateList(context, R.color.black))
                }
            }
            ll.addView(view)

            if(i<totalStep-1){
                val betweenView = View(context)
                val lpBetween = LinearLayout.LayoutParams(100, 5)
                betweenView.layoutParams = lpBetween
                if (i < currentStep-1){
                    betweenView.background = AppCompatResources.getDrawable(context, R.drawable.solid_green_corner_7)
                }else{
                    betweenView.background = AppCompatResources.getDrawable(context, R.drawable.light_grey_solid_corner_7)
                }

                ll.addView(betweenView)
            }
        }
    }
}