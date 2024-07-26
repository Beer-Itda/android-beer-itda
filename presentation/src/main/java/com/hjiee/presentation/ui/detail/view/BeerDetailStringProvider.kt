package com.hjiee.presentation.ui.detail.view

import android.content.Context
import com.hjiee.presentation.R
import com.hjiee.core.provider.IStringResourceProvider
import javax.inject.Inject

class BeerDetailStringProvider @Inject constructor(
    private val context: Context
) : IStringResourceProvider {

    fun getError(): String = getStringRes(com.hjiee.core.R.string.error_message)
    fun getDeleteMessage(): String = getStringRes(R.string.success_deleted_review)


    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}