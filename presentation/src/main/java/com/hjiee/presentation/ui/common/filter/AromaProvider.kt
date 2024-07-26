package com.hjiee.presentation.ui.common.filter

import com.hjiee.presentation.ui.filter.aroma.item.small.AromaItemViewModel
import com.google.gson.Gson
import com.hjiee.core.provider.SharedPreferenceProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject
import kotlin.reflect.KProperty

@ExperimentalCoroutinesApi
class AromaProvider @Inject constructor(
    private val preference: SharedPreferenceProvider
) : IFilterProvider<Any, List<AromaItemViewModel>?> {

    companion object {
        private const val KEY_EXTRA_FILTER_AROMA = "aroma"
    }

    private val channel: ConflatedBroadcastChannel<List<AromaItemViewModel>?>
        get() = ConflatedBroadcastChannel(data)

    override fun getFlow(): Flow<List<AromaItemViewModel>?> = channel.asFlow()

    override var data: List<AromaItemViewModel>? by this
    override fun getNames(): List<String>? {
        return data?.map {
            it.name
        }.orEmpty()
    }

    override fun getDefaultValue(): List<AromaItemViewModel> {
        val jsonValue = preference.getPreferenceString(KEY_EXTRA_FILTER_AROMA)
        return Gson().fromJson(jsonValue, (Array<AromaItemViewModel>::class.java))
            .orEmpty()
            .toList()
    }

    override fun setValue(
        thisRef: Any,
        property: KProperty<*>,
        value: List<AromaItemViewModel>?
    ) {
        preference.setValue(KEY_EXTRA_FILTER_AROMA, Gson().toJson(value))
        channel.trySend(value)
    }

    override fun getValue(
        thisRef: Any,
        property: KProperty<*>
    ): List<AromaItemViewModel> =
        getDefaultValue()
}