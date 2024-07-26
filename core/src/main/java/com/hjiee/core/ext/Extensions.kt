package com.hjiee.core.ext

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * int
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Int?.orZero(): Int = this ?: 0
fun Int?.orDefault(value : Int): Int = this ?: value


/**
 * float
 */
fun Float.toPx(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this,
    context.resources.displayMetrics
).toInt()

val Float.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

fun Float?.orZero(): Float = this ?: 0f
fun Float?.orDefault(value: Float): Float = this ?: value

/**
 * long
 */
fun Long?.orZero(): Long = this ?: 0L
fun Long?.orDefault(value: Long): Long = this ?: value

/**
 * boolean
 */
fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.toggle(): Boolean = this?.not() ?: false

/**
 * string
 */
fun String?.orDefault(default: String): String = this ?: default