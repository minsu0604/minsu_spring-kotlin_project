package com.example.myapplication.Login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivitySearchAddressBinding
import com.example.myapplication.utils.Const

class SearchAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivitySearchAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.webView!!.settings.javaScriptEnabled = true
        binding.webView!!.addJavascriptInterface(MyJavaScriptInterface(), "Android")
        binding.webView!!.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                // 위 웹페이지가 load가 끝나면 코드에서 작성했던 script 을 호출한다.
                view.loadUrl("javascript:sample2_execDaumPostcode();")
            }
        }
        binding.webView.loadUrl(Const.SERVER_BASE_URL +"/search-address")
    }

    inner class MyJavaScriptInterface {
        @JavascriptInterface
        fun processDATA(data: String?) {
            // 주소검색창에서 주소를 선택하면 그 결과값이 data 에 들어오기 떄문에 그것을
            // 받아서 내가 만드는 앱 페이지로 넘기면 끝.
            val intent = Intent()
            intent.putExtra("data", data)
            Log.d("receive data", data.toString())

            setResult(RESULT_OK, intent)
            finish()
        }
    }

}