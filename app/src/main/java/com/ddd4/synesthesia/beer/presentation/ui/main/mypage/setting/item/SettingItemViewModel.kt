package com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.item

import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.hjiee.core.event.SelectEventNotifier
import com.ddd4.synesthesia.beer.presentation.ui.main.mypage.setting.model.SettingClickEntity

data class SettingItemViewModel(
    val title: String,
    val type: InfomationsType,
    val detailInfomation: String? = null,
    private val eventNotifier: SelectEventNotifier
) {
    fun clickItem() {
        eventNotifier.notifySelectEvent(SettingClickEntity.ItemClick(this))
    }
}