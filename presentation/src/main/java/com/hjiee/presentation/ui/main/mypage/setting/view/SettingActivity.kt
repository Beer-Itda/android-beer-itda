package com.hjiee.presentation.ui.main.mypage.setting.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjiee.ThemeHelper
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.data.source.local.InfomationsData
import com.hjiee.presentation.R
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.databinding.ActivityMyPageSettingBinding
import com.hjiee.presentation.ui.login.view.LoginActivity
import com.hjiee.presentation.ui.main.mypage.delete.view.MyAccountDeleteActivity
import com.hjiee.presentation.ui.main.mypage.setting.model.SettingActionEntity
import com.hjiee.presentation.ui.main.mypage.setting.model.SettingClickEntity
import com.hjiee.presentation.ui.main.mypage.setting.viewmodel.SettingViewModel
import com.hjiee.presentation.ui.webview.view.WebViewActivity
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.showToast
import com.hjiee.presentation.util.ext.start
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingActivity :
    BaseActivity<ActivityMyPageSettingBinding>(R.layout.activity_my_page_setting) {

    private val viewModel by viewModels<SettingViewModel>()
    private val settingAdapter by lazy { SettingAdapter() }
    private val themeList by lazy { resources.getStringArray(R.array.theme_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
        initObserver()
        viewModel.init()
    }

    override fun initBind() {
        binding.run {
            includeToolbar.toolbar.setNavigationOnClickListener { finish() }
            with(rvSetting) {
                adapter = settingAdapter
            }
        }
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
        observeHandledEvent(viewModel.event.select) {
            handleSelectEvent(it)
        }
        observeHandledEvent(viewModel.event.throwable) {
            if (it.first.message.isNullOrEmpty().not()) {
                showToast(it.first.message.orEmpty())
            }
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is SettingActionEntity.UpdateItem -> {
                settingAdapter.clear()
                settingAdapter.addAll(entity.items)
            }
            is SettingActionEntity.LogOut -> {
                preference.clear()
                start<LoginActivity>(LoginActivity.getTaskIntent(this))
            }
        }
    }

    override fun handleSelectEvent(entity: ItemClickEntity) {
        when (entity) {
            is SettingClickEntity.ItemClick -> {
                informationsEvent(entity.item.title)
            }
        }
    }

    private fun informationsEvent(section: String) {
        when (section) {
            // 공지사항
            InfomationsData.NOTICE.title -> {
                moveToNotice()
            }
            // 문의하기
            InfomationsData.CONTACT.title -> {
                moveToContact()
            }
            // 릴리즈 노트
            InfomationsData.RELEASE_NOTE.title -> {
                moveToReleaseNote()
            }
            // 오픈소스 라이브러리
            InfomationsData.OPEN_SOURCE_LIB.title -> {
                start<OssLicensesMenuActivity>()
            }
            // 플레이 스토어 평가
            InfomationsData.PLAY_STORE.title -> {
                moveToPlayStore()
            }
            // 개인정보처리방침
            InfomationsData.PRIVACY_POLICY.title -> {
                moveToPrivacyPolicy()
            }
            // 이용약관
            InfomationsData.TERMS_OF_USE.title -> {
                moveToTermsOfUse()
            }
            // 로그아웃
            InfomationsData.LOGOUT.title -> {
                showDialog()
            }
            // 회원탈퇴
            InfomationsData.UNLINK.title -> {
                start<MyAccountDeleteActivity>()
            }
            // 알림설정
            InfomationsData.PUSH.title -> {
                showToast(resources.getString(R.string.please_wait_for_a_little_while))
            }
            // 테마설정
            InfomationsData.THEME.title -> {
                MaterialAlertDialogBuilder(this@SettingActivity, R.style.Dialog)
                    .setTitle(getString(R.string.setting_theme))
                    .setSingleChoiceItems(themeList, viewModel.getSelectedThemePosition()) { dialog, position ->
                        viewModel.saveTheme(position)
                        ThemeHelper.getThemeMode(position).let {
                            ThemeHelper.applyTheme(it)
                        }
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(this@SettingActivity)
            .setMessage(getString(R.string.logout_message))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.logOut()
            }
            .setNegativeButton(getString(R.string.no), null)
            .show()
    }

    /**
     * 이용약관
     */
    private fun moveToTermsOfUse() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(com.hjiee.core.R.string.terms_of_use))
            )
        )
    }

    /**
     * 개인정보 처리방침
     */
    private fun moveToPrivacyPolicy() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(com.hjiee.core.R.string.privacy_policy))
            )
        )
    }

    /**
     * 공지사항
     */
    private fun moveToNotice() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(com.hjiee.core.R.string.notice))
            )
        )
    }

    /**
     * 문의하기
     */
    private fun moveToContact() {
        start<WebViewActivity>(
            WebViewActivity.getInquiryIntent(
                context = this
            )
        )
    }

    /**
     * 릴리즈 노트
     */
    private fun moveToReleaseNote() {
        start<WebViewActivity>(
            WebViewActivity.getIntent(
                context = this,
                url = preference.getPreferenceString(getString(com.hjiee.core.R.string.release_note))
            )
        )
    }

    companion object {

        fun getIntent(context: Context): Intent {
            return Intent(context, SettingActivity::class.java).apply {

            }
        }
    }
}