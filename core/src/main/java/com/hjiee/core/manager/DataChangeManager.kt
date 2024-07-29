package com.hjiee.core.manager

import android.content.Context
import android.content.SharedPreferences
import com.hjiee.core.ext.orZero
import com.hjiee.core.provider.SharedPreferenceProvider.Companion.SHARED_PRIVATE_KEY
import com.hjiee.core.util.log.L

class DataChangeManager private constructor() {

    companion object {
        private const val prefix = "key_changed"
        private var instance: DataChangeManager? = null
        private var preference: SharedPreferences? = null

        fun init(context: Context) {
            initPreference(context)
        }

        private fun initPreference(context: Context) {
            preference = context.applicationContext.getSharedPreferences(
                SHARED_PRIVATE_KEY,
                Context.MODE_PRIVATE
            ).apply {
                edit()?.run {
                    Change.entries.map {
                        remove(getKeyName(it)).apply()
                    }
                }
            }
        }

        fun getInstance(): DataChangeManager {
            return instance ?: DataChangeManager().also {
                instance = it
            }
        }

        /**
         * 변경된 상태 정보를 반환한다.
         */
        fun getStatus(type: Change): Long {
            return preference?.getLong(getKeyName(type), 0L).orZero()
        }

        /**
         * 데이터가 변경되면 값을 변경한다.
         */
        fun changed(type: Change) {
            runCatching {
                val keyName = getKeyName(type)
                var status = preference?.getLong(keyName, 0L).orZero()
                preference?.edit()?.run {
                    putLong(keyName, ++status).apply()
                }
            }.onFailure {
                L.d("${type.name} : ${getStatus(type)}")
                L.e(it)
            }
        }

        private fun getKeyName(type: Change): String {
            return "${prefix}_${type.name.lowercase()}"
        }
    }
}

enum class Change {
    REVIEW,
    FAVORITE,
    RECOMMEND_CONFIGURATION
}