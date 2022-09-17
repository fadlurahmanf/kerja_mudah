package com.app.kerja_mudah.custom_view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.app.kerja_mudah.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class KerjaMudahProfilePicture @JvmOverloads constructor(
    context: Context, attrs:AttributeSet
) : ConstraintLayout(context, attrs) {
    private var attributes : TypedArray
    private var imageView:ImageView

    private var photo:String ?= null

    init {
        inflate(context, R.layout.kerja_mudah_profile_picture, this)
        imageView = findViewById(R.id.iv_profile_picture)
        attributes = context.obtainStyledAttributes(attrs, R.styleable.KerjaMudahProfilePicture)
        photo = attributes.getString(R.styleable.KerjaMudahProfilePicture_photo)
    }

    fun setPhoto(url:String?) {
        if (url != null){
            Log.d("Set Photo", "Url : $url")
            imageView.setBackgroundColor(Color.TRANSPARENT)
            imageView.setImageDrawable(null)
            imageView.imageTintList = null
            Glide.with(imageView)
                .load(url)
                .listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        setPhoto(null)
                        return true
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                .centerCrop()
                .into(imageView)
        }else{
            Log.d("Set Photo", "Url : null")
            imageView.setBackgroundColor(ContextCompat.getColor(context, R.color.yellow))
            imageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_person))
            imageView.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white))
        }
    }
}