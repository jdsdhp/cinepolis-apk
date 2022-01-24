package com.jdsdhp.cinepoliapp.ui.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.annotation.DimenRes

/** makes visible a view. */
fun View.visible() {
    visibility = View.VISIBLE
}

/** makes invisible a view. */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/** makes gone a view. */
fun View.gone() {
    visibility = View.GONE
}

fun View.setSafeOnClickListener(onSafeClick: ((View) -> Unit)?) {
    val safeClickListener = SafeClickListener {
        if (onSafeClick != null) onSafeClick(it)
    }
    setOnClickListener(if (onSafeClick != null) safeClickListener else null)
}

fun View.setSafeOnLongClickListener(onSafeLongClick: ((View) -> Unit)?): Boolean {
    val safeClickListener = SafeLongClickListener {
        if (onSafeLongClick != null) onSafeLongClick(it)
    }
    setOnLongClickListener(if (onSafeLongClick != null) safeClickListener else null)
    return true
}

@ColorInt
fun Context.getThemeAttrColor(@AttrRes attr: Int): Int {
    val typedArray = this.obtainStyledAttributes(intArrayOf(attr))
    return try {
        typedArray.getColor(0, 0)
    } finally {
        typedArray.recycle()
    }
}

fun Context.getThemeAttrDrawable(@AttrRes attr: Int): Drawable? {
    val typedArray = this.obtainStyledAttributes(intArrayOf(attr))
    return try {
        typedArray.getDrawable(0)
    } finally {
        typedArray.recycle()
    }
}

fun Context.getDimension(@DimenRes dimenRes: Int): Float = resources.getDimension(dimenRes)