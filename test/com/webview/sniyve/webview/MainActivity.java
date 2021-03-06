package com.webview.sniyve.webview;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.sniyve.ai.AppInterface;
import com.sniyve.ai.Callback;

import java.util.Map;

public class MainActivity extends ActionBarActivity {

    private WebView webView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppInterface.getInstance().init(this,"com.webview.sniyve.webview.controllers");
        initWebView();
    }

    public void initWebView(){
        webView = (WebView)this.findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString(settings.getUserAgentString() + " gomeplus");
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        webView.setWebViewClient(new AppInterfaceWebViewClient(webView));

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                return super.onJsAlert(view, url, message, result);
            }
        });

        AppInterface.getInstance().initJsBridge(webView).subscribe("onClick", new Callback() {
            @Override
            public void call(Map<String, Object> params) {
                TextView textView = (TextView) MainActivity.this.findViewById(R.id.textView);
                textView.setText((String) params.get("value"));
            }
        });
        webView.loadUrl("http://h5-pre.gomeplus.com/index/index");
        //webView.loadUrl("http://h5-pre.gomeplus.com/shop/index?shopId=1");
       // webView.loadUrl("http://192.168.2.101:63341/wsworkspace/AppInterface/demo.html");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
