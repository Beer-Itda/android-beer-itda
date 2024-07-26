package com.hjiee.presentation.ui.webview.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import androidx.core.view.isVisible
import com.hjiee.presentation.R
import com.hjiee.presentation.databinding.ActivityWebviewBinding
import com.hjiee.presentation.base.BaseActivity
import com.hjiee.presentation.ui.webview.CustomWebChromeClient
import com.hjiee.presentation.ui.webview.CustomWebViewClient
import com.hjiee.presentation.ui.webview.model.WebViewActionEntity
import com.hjiee.core.event.ActionEventNotifier
import com.hjiee.core.event.entity.ActionEntity


class WebViewActivity : BaseActivity<ActivityWebviewBinding>(R.layout.activity_webview),
    ActionEventNotifier {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBind()
    }

    override fun initBind() {
        binding.apply {
            webview.apply {
                webViewClient = CustomWebViewClient(this@WebViewActivity)
                webChromeClient = CustomWebChromeClient(this@WebViewActivity)
                settings.javaScriptEnabled = true // 웹페이지 자바스클비트 허용 여부
                settings.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
                settings.javaScriptCanOpenWindowsAutomatically = false // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
                settings.loadWithOverviewMode = true // 메타태그 허용 여부
                settings.useWideViewPort = true // 화면 사이즈 맞추기 허용 여부
                settings.setSupportZoom(true) // 화면 줌 허용 여부
                settings.builtInZoomControls = false // 화면 확대 축소 허용 여부
                settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN // 컨텐츠 사이즈 맞추기
                settings.cacheMode = WebSettings.LOAD_NO_CACHE // 브라우저 캐시 허용 여부
                settings.domStorageEnabled = true // 로컬저장소 허용 여부
                intent?.getStringExtra(WEBVIEW_URL)?.let {
                    loadUrl(it)
                }
            }
        }
    }

    override fun onBackPressed() {
        when (binding.webview.canGoBack()) {
            true -> {
                binding.webview.goBack()
            }
            false -> {
                finish()
            }
        }
    }

    override fun notifyActionEvent(entity: ActionEntity) {
        when (entity) {
            is WebViewActionEntity.PageLoadFinished -> {
                binding.loadingProgress.isVisible = false
            }
            is WebViewActionEntity.ProgressChanged -> {
                binding.loadingProgress.progress = entity.progress
            }
        }
    }

    companion object {
        const val WEBVIEW_URL = "webview_url"

        fun getIntent(
            context: Context,
            url: String?
        ): Intent {
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(WEBVIEW_URL, url)
            }
        }

        fun getInquiryIntent(
            context: Context
        ): Intent {
            val inquiryUrl = context.getString(R.string.beer_itda_inquiry_url)
            return Intent(context, WebViewActivity::class.java).apply {
                putExtra(WEBVIEW_URL, inquiryUrl)
            }
        }
    }
}