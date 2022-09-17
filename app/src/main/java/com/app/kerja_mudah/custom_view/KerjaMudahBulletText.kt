package com.app.kerja_mudah.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.app.kerja_mudah.R

class KerjaMudahBulletText @JvmOverloads constructor(
    context: Context, attrs:AttributeSet
) : LinearLayout(context, attrs) {
    private var attributes : TypedArray
    private var tv:TextView

    private var bulletText:String = ""

    init {
        inflate(context, R.layout.kerja_mudah_bullet_text, this)

        attributes = context.obtainStyledAttributes(attrs, R.styleable.BulletTextKerjaMudah)
        tv = findViewById(R.id.tv)

        bulletText = attributes.getString(R.styleable.BulletTextKerjaMudah_bullet_text) ?: ""
        setText(bulletText)
    }

    fun setText(text:String){
        tv.text = text
    }
}