package com.hjiee.presentation.ui.splash.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivitySplashBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.login.model.LoginActionEntity
import com.hjiee.presentation.ui.login.view.LoginActivity
import com.hjiee.presentation.ui.main.view.MainActivity
import com.hjiee.presentation.ui.splash.model.SplashActionEntity
import com.hjiee.presentation.ui.splash.viewmodel.SplashViewModel
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.manager.UpdateRequiredStatus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(R.layout.activity_splash) {

    private val viewModel by viewModels<SplashViewModel>()
    private val dialog by lazy {
        AlertDialog.Builder(this, R.style.Dialog).apply {
            setCancelable(true)
            setPositiveButton(getString(R.string.update)) { _, _ ->
                moveToPlayStore()
            }
            if (viewModel.updateRequiredStatus.value == UpdateRequiredStatus.Recommend) {
                setNegativeButton(getString(R.string.no_update)) { _, _ ->
//                    viewModel.autoLogin()
                }
            }
        }.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
//        start<LoginActivity>()
    }

    override fun onResume() {
        super.onResume()
//        viewModel.checkForUpdate()
    }

    override fun initObserver() {
        observeHandledEvent(viewModel.event.action) {
            handleActionEvent(it)
        }
    }

    override fun handleActionEvent(entity: ActionEntity) {
        when (entity) {
            is LoginActionEntity.SuccessLogin -> {
                start<MainActivity>()
                finish()
            }
            is LoginActionEntity.FailLogin -> {
                start<LoginActivity>()
                finish()
            }
            is SplashActionEntity.ForceUpdate -> {
                showDialog(
                    title = getString(R.string.force_update_title),
                    message = entity.message
                )
            }
            is SplashActionEntity.RecommendUpdate -> {
                showDialog(
                    title = getString(R.string.recommend_update_title),
                    message = entity.message
                )
            }
        }
    }

    private fun showDialog(title: String, message: String) {
        if (dialog.isShowing) {
            return
        }

        dialog.apply {
            setTitle(title)
            setMessage(message)

        }.show()
    }

    override fun onBackPressed() {
//     Nothing
    }
}