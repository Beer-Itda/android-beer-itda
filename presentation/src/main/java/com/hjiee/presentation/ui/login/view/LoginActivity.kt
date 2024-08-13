package com.hjiee.presentation.ui.login.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextClock
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.core.Consts
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityLoginBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.base.model.ErrorActionEntity
import com.hjiee.presentation.ui.login.model.LoginActionEntity
import com.hjiee.presentation.ui.login.viewmodel.LoginViewModel
import com.hjiee.presentation.ui.main.view.MainActivity
import com.hjiee.presentation.util.HyperLinkMovement
import com.hjiee.presentation.util.ext.observeHandledEvent
import com.hjiee.presentation.util.ext.showToast
import com.hjiee.presentation.util.ext.start
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.manager.VersionManager
import com.hjiee.core.util.listener.setOnDebounceClickListener
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var versionManager: VersionManager
    private val loginViewModel by viewModels<LoginViewModel>()
    private val message by lazy { intent.getStringExtra(KEY_LOGIN) }
    private val kakaoTalkLoginCallback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.tag("tokenInfo").e(error)
            showToast(getString(R.string.fail_login))
        } else if (token != null) {
            Timber.tag("tokenInfo").d(token.accessToken)
            loginViewModel.login(token.accessToken)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initBind()
//        initObserver()
//        val binding = ActivityLoginBinding.inflate(layoutInflater)
//        binding.version.text = versionManager.version

        setContentView(R.layout.activity_login)
        val version = findViewById<TextView>(R.id.version)
        version.text = versionManager.version
    }

    override fun onResume() {
        super.onResume()
//        val version = findViewById<TextView>(R.id.version)
//        version.text = versionManager.version
    }

//    override fun initBind() {
//        binding.apply {
//            viewModel = loginViewModel
//            version.text = versionManager.version
//            tvLogin.setOnDebounceClickListener {
////                startLogin()
//            }
//            with(tvLoginNotice) {
//                text = loginViewModel.noticeMessage
//                movementMethod = HyperLinkMovement()
//            }
//        }
//    }

    private fun startLogin() {
        // 카카오톡으로 로그인
        LoginClient.instance.apply {
            if (isKakaoTalkLoginAvailable(this@LoginActivity)) {
                loginWithKakaoTalk(context = this@LoginActivity, callback = kakaoTalkLoginCallback)
            } else {
                loginWithKakaoAccount(this@LoginActivity, callback = kakaoTalkLoginCallback)
            }
        }
    }

//    override fun initObserver() {
//        observeHandledEvent(loginViewModel.event.action) {
//            handleActionEvent(it)
//        }
//    }
//
//    override fun handleActionEvent(entity: ActionEntity) {
//        when (entity) {
//            is LoginActionEntity.SuccessLogin -> {
//                start<MainActivity>()
//                finish()
//            }
//
//            is ErrorActionEntity.ShowErrorMessage -> {
//                showToast(entity.message)
//            }
//        }
//    }

    companion object {
        const val KEY_LOGIN = "login"

        /**
         * 비회원 로그인 후 로그인처리가 필요할때 사용
         */
        @JvmStatic
        fun getIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {

            }
        }

        /**
         * 로그아웃, 회원탈퇴시 사용
         */
        @JvmStatic
        fun getTaskIntent(context: Context): Intent {
            return Intent(context, LoginActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
        }
    }
}