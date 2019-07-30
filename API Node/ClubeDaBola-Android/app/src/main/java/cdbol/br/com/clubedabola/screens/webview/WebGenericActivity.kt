package cdbol.br.com.clubedabola.screens.webview

import android.annotation.SuppressLint
import android.os.Bundle

import android.support.v7.app.AppCompatActivity

import cdbol.br.com.clubedabola.R

import kotlinx.android.synthetic.main.item_webview.*


class WebGenericActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.item_webview)

        var url = intent.extras.get("url") as String
        web_view.settings.javaScriptEnabled = true

        if (url.contains(".pdf")) {
            web_view.loadUrl("https://docs.google.com/viewer?url=$url")
        }else{
            web_view.loadUrl(url)

        }


    }


}
