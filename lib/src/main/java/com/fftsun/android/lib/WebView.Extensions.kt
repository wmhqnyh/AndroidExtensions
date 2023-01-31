package com.fftsun.android.lib

import android.os.Build
import android.webkit.CookieManager
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.annotation.MainThread

fun WebView.initWebView() {
    scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
    with(settings) {
        javaScriptEnabled = true
        loadWithOverviewMode = true
        useWideViewPort = true
        databaseEnabled = true
        domStorageEnabled = true
        allowFileAccess = true
        javaScriptCanOpenWindowsAutomatically = true
        cacheMode = WebSettings.LOAD_NO_CACHE
        textZoom = 100
    }

    val webCookieManager = CookieManager.getInstance()
    webCookieManager.setAcceptCookie(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        webCookieManager.setAcceptThirdPartyCookies(this, true)
    }
}

@MainThread
fun WebView.runJavascript(script: String, resultCallback: ValueCallback<String>? = null) {
    evaluateJavascript(script, resultCallback)
}