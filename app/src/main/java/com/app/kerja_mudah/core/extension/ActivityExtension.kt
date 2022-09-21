package com.app.kerja_mudah.core.extension

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import androidx.core.view.WindowCompat

@Suppress("DEPRECATION")
fun Activity.setTransparentStatusBar() {
    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    window.statusBarColor = Color.TRANSPARENT
}

fun Activity.setLightStatusBarColor(isTrue:Boolean = true){
    WindowCompat.getInsetsController(window, window.decorView)?.isAppearanceLightStatusBars = isTrue
}