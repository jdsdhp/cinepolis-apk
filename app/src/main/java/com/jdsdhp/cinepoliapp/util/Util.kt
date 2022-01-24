package com.jdsdhp.cinepoliapp.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.regex.Pattern

fun Activity.hideSoftKeyboard() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val currentFocus = currentFocus ?: View(this)
    imm.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
}

fun String.isValidEmail(): Boolean = Pattern
    .compile("^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
    .matcher(this)
    .matches()

fun Float.roundDecimals(newScale: Int = 1, roundingMode: RoundingMode = RoundingMode.HALF_EVEN) =
    this.toBigDecimal().setScale(newScale, roundingMode).toFloat()

fun String.toDate(formatStyle: FormatStyle = FormatStyle.MEDIUM): String {
    val date = LocalDate.parse(this.substring(0, 10))
    val formatter = DateTimeFormatter.ofLocalizedDate(formatStyle)
    return date.format(formatter)
}