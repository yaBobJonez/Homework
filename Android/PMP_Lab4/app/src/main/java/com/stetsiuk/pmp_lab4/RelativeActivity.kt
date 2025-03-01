package com.stetsiuk.pmp_lab4

import android.os.Bundle
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RelativeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_relative)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val search_btn = findViewById<Button>(R.id.search_btn)

        val params = search_btn.layoutParams as RelativeLayout.LayoutParams
        params.removeRule(RelativeLayout.ALIGN_PARENT_TOP)
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
        search_btn.layoutParams = params

        val search = findViewById<EditText>(R.id.search_edit)
        val webview = findViewById<WebView>(R.id.webview)
        webview.webViewClient = WebClient(search)
        webview.settings.javaScriptEnabled = true

        search_btn.setOnClickListener {
            webview.loadUrl(search.text.toString())
        }

        findViewById<Button>(R.id.back_btn).setOnClickListener {
            if (!webview.canGoBack()) return@setOnClickListener
            webview.goBack()
        }
        findViewById<Button>(R.id.forward_btn).setOnClickListener {
            if (!webview.canGoForward()) return@setOnClickListener
            webview.goForward()
        }
        findViewById<Button>(R.id.refresh_btn).setOnClickListener {
            webview.reload()
        }

        findViewById<Button>(R.id.search_btn).performClick()
    }
}