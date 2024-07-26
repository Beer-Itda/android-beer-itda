package com.hjiee.presentation.util.sort

import android.content.Context
import com.hjiee.presentation.R
import com.hjiee.core.provider.SharedPreferenceProvider
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

enum class SortType(val value: String?) {
    Default(null),
    Rating("rate_avg_desc"),
    Review("review_count_desc")
}

class SortImpl(val preference: SharedPreferenceProvider, context: Context) : SortSetting,
    ReadWriteProperty<Any, SortType> {

    private val key = context.resources.getString(R.string.key_sort_type)
    private val channel = ConflatedBroadcastChannel(getDefaultValue())

    override var sortType: SortType by this

    override fun getSort(): Flow<SortType> = channel.asFlow()

    private fun getDefaultValue(): SortType {
        val storeValue = preference.getPreferenceString(key)
        return if (storeValue.isNullOrEmpty()) SortType.Default else SortType.valueOf(storeValue)
    }

    override fun setValue(thisRef: Any, property: KProperty<*>, value: SortType) {
        preference.setValue(key, value.toString())
        channel.trySend(value)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): SortType = getDefaultValue()

}