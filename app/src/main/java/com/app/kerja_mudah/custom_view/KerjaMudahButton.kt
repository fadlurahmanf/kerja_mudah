package com.app.kerja_mudah.custom_view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.Button
import android.widget.LinearLayout
import androidx.annotation.Nullable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R

class KerjaMudahButton @JvmOverloads constructor(
    context: Context, attrs:AttributeSet
) : LinearLayout(context, attrs) {

    private var attributes : TypedArray
    private var button:Button

    private var text: String
    private var type:Int
    private var buttonEnabled:Boolean
    private var applyCorner:Boolean

    init {
        inflate(context, R.layout.kerja_mudah_button, this)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.ButtonKerjaMudah)

        button = findViewById(R.id.button)

        text = attributes.getString(R.styleable.ButtonKerjaMudah_button_text) ?: ""
        type = attributes.getInt(R.styleable.ButtonKerjaMudah_button_type, 0)
        buttonEnabled = attributes.getBoolean(R.styleable.ButtonKerjaMudah_button_enabled, true)
        applyCorner = attributes.getBoolean(R.styleable.ButtonKerjaMudah_apply_corner, true)

        setType(type)
        setText(text)
        setButtonEnabled(buttonEnabled)
    }

    fun setType(type: Int) {
        this.type = type
        refreshButtonStyle()
    }

    private fun refreshButtonStyle(){
        button.background = if (applyCorner){
            if (buttonEnabled){
                if (type == 0){
                    button.setTextColor(ContextCompat.getColor(context, R.color.white))
                    AppCompatResources.getDrawable(context, R.drawable.solid_green_corner_7)
                }else {
                    button.setTextColor(ContextCompat.getColor(context, R.color.green))
                    AppCompatResources.getDrawable(context, R.drawable.solid_white_stroke_green_corner_7)
                }
            }else{
                if (type == 0){
                    button.setTextColor(ContextCompat.getColor(context, R.color.black))
                    AppCompatResources.getDrawable(context, R.drawable.solid_light_grey_corner_7)
                }else{
                    button.setTextColor(ContextCompat.getColor(context, R.color.green))
                    AppCompatResources.getDrawable(context, R.drawable.solid_white_stroke_green_corner_7)
                }
            }
        } else{
            if (buttonEnabled){
                if (type == 0){
                    button.setTextColor(ContextCompat.getColor(context, R.color.white))
                    AppCompatResources.getDrawable(context, R.color.green)
                }else {
                    button.setTextColor(ContextCompat.getColor(context, R.color.green))
                    AppCompatResources.getDrawable(context, R.drawable.solid_white_stroke_green)
                }
            }else{
                if (type == 0){
                    button.setTextColor(ContextCompat.getColor(context, R.color.black))
                    AppCompatResources.getDrawable(context, R.color.light_grey)
                }else{
                    button.setTextColor(ContextCompat.getColor(context, R.color.green))
                    AppCompatResources.getDrawable(context, R.drawable.solid_white_stroke_green_corner_7)
                }
            }
        }
    }

    fun setText(text:String){
        button.text = text
    }

    fun setContentButtonPadding(left:Int = 0, right:Int = 0, top:Int = 0, bottom:Int = 0){
        button.setPadding(left, top, right, bottom)
    }

    fun setButtonEnabled(enabled:Boolean){
        buttonEnabled = enabled
        refreshButtonStyle()
    }

    fun setOnClickListener(@Nullable listener:()->Unit){
        button.setOnClickListener {
            listener()
        }
    }
}