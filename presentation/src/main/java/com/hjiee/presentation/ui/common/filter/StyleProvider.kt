package com.hjiee.presentation.ui.common.filter

import com.hjiee.presentation.ui.filter.style.item.small.StyleSmallItemViewModel
import com.google.gson.Gson
import com.hjiee.core.provider.SharedPreferenceProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import kotlin.reflect.KProperty

@ExperimentalCoroutinesApi
class StyleProvider @Inject constructor(
    private val preference: SharedPreferenceProvider
) : IFilterProvider<Any, List<StyleSmallItemViewModel>?> {

    companion object {
        private const val KEY_EXTRA_FILTER_STYLE = "style"
    }

    private val channel: ConflatedBroadcastChannel<List<StyleSmallItemViewModel>?>
        get() = ConflatedBroadcastChannel(data)

    override fun getFlow(): Flow<List<StyleSmallItemViewModel>?> = channel.asFlow()

    override var data: List<StyleSmallItemViewModel>? by this

    override fun getNames(): List<String>? {
        return data?.map { it.smallName }.orEmpty()
    }

    override fun getDefaultValue(): List<StyleSmallItemViewModel> {
        val jsonValue = preference.getPreferenceString(KEY_EXTRA_FILTER_STYLE)
        return Gson().fromJson(jsonValue, Array<StyleSmallItemViewModel>::class.java)
            .orEmpty()
            .toList()
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: List<StyleSmallItemViewModel>?
    ) {
        preference.setValue(KEY_EXTRA_FILTER_STYLE, Gson().toJson(value))
        channel.trySend(value)
    }

    override fun getValue(thisRef: Any, property: KProperty<*>): List<StyleSmallItemViewModel> {
        return getDefaultValue()
    }
}