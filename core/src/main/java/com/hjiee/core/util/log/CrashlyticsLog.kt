package com.hjiee.core.util.log

import com.google.firebase.crashlytics.ktx.crashlytics
import com.google.firebase.ktx.BuildConfig
import com.google.firebase.ktx.Firebase

object CrashlyticsLog {

    fun init() {
        Firebase.crashlytics.setCrashlyticsCollectionEnabled(!BuildConfig.DEBUG)
    }

    fun recordException(throwable: Throwable) {
        Firebase.crashlytics.recordException(throwable)
    }

    fun log(message: String) {
        Firebase.crashlytics.log(message)
    }

    fun setCustomKey(key: String, value: String) {
        Firebase.crashlytics.setCustomKey(key, value)
    }
}