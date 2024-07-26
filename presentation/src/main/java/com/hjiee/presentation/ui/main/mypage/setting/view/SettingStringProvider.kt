package com.hjiee.presentation.ui.main.mypage.setting.view

import android.content.Context
import com.hjiee.presentation.R
import com.hjiee.core.provider.IStringResourceProvider
import javax.inject.Inject

class SettingStringProvider @Inject constructor(
    private val context: Context
) : IStringResourceProvider {

    fun getError() = getStringRes(com.hjiee.core.R.string.error_message)

    val selectedTheme get() = context.resources.getStringArray(R.array.theme_list)

    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}