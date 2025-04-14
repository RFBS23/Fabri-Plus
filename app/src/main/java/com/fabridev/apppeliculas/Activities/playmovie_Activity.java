package com.fabridev.apppeliculas.Activities;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import android.net.Uri;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.ui.PlayerView;

import com.fabridev.apppeliculas.R;

public class playmovie_Activity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_playmovie);
        webView = findViewById(R.id.web_view);
        String videoUrl = getIntent().getStringExtra("videoUrl");
        if (videoUrl != null && !videoUrl.isEmpty()) {
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            // Configuración del cliente personalizado
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    String url = request.getUrl().toString();

                    // Bloquea anuncios o redirecciones no deseadas
                    if (url.contains("ads") || url.contains("adservice") || url.contains("redirect")) {
                        return true; // Bloquear
                    }
                    return false; // Permitir el resto de URLs
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    // Inyectar JavaScript para eliminar elementos de anuncios
                    String adBlockScript = "javascript:(function() {" +
                            "var ads = document.querySelectorAll('[id*=ad], [class*=ad], [id*=banner], [class*=banner]');" +
                            "for (var i = 0; i < ads.length; i++) { ads[i].style.display = 'none'; }" +
                            "})();";
                    view.evaluateJavascript(adBlockScript, null);
                }
            });
            webView.loadUrl(videoUrl);
        } else {
            Toast.makeText(this, "URL no válida", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.destroy();
        }
    }
}