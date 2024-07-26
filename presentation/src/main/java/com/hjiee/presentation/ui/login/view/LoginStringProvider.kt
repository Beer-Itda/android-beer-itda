package com.hjiee.presentation.ui.login.view

import android.content.Context
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import com.hjiee.core.provider.IStringResourceProvider
import com.hjiee.core.provider.SharedPreferenceProvider
import com.hjiee.presentation.R
import javax.inject.Inject

class LoginStringProvider @Inject constructor(
    private val context: Context,
    private val preference: SharedPreferenceProvider
) : IStringResourceProvider {

    fun getNoticeMessage(): CharSequence {
        return buildSpannedString {
            append(
                HtmlCompat.fromHtml(
                    String.format(
                        getStringRes(R.string.login_notice),
                        preference.getPreferenceString(getStringRes(com.hjiee.core.R.string.terms_of_use)),
                        preference.getPreferenceString(getStringRes(com.hjiee.core.R.string.privacy_policy))
                    ),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
        }
    }

    fun loginFail(): String {
        return getStringRes(R.string.fail_login)
    }

    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}