package com.hjiee.core.util.log

import com.google.firebase.ktx.BuildConfig
import com.hjiee.core.util.log.timber.CrashlyticsTree
import timber.log.Timber

object L {

    const val TAG = "BEER-ITDA"

    fun init() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        Timber.plant(CrashlyticsTree())
    }

    fun v(
        message: String?
    ) {
        Timber.v(message)
    }

    fun v(
        tag: String,
        message: String?
    ) {
        Timber.tag(tag).v(message)
    }

    fun i(
        message: String?
    ) {
        Timber.i(message)
    }

    fun i(
        tag: String,
        message: String?
    ) {
        Timber.tag(tag).i(message)
    }

    fun d(
        message: String?
    ) {
        Timber.d(message)
    }

    fun d(
        tag: String,
        message: String?
    ) {
        Timber.tag(tag).d(message)
    }

    fun w(
        message: String?
    ) {
        Timber.w(message)
    }

    fun w(
        tag: String,
        message: String?
    ) {
        Timber.tag(tag).w(message)
    }

    fun e(
        throwable: Throwable?
    ) {
        Timber.e(throwable)
    }

    fun e(
        throwable: Throwable?,
        message: String?
    ) {
        Timber.e(throwable, message)
    }

    fun e(
        tag: String,
        throwable: Throwable?
    ) {
        Timber.tag(tag).e(throwable)
    }
}