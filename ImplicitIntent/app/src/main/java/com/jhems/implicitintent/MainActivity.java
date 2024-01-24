package com.jhems.implicitintent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.webkit.WebView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private EditText urlEditText;
    private Button openWebPageButton;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlEditText = findViewById(R.id.urlEditText);
        openWebPageButton = findViewById(R.id.openWebPageButton);
        webView = findViewById(R.id.webView);

        openWebPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = urlEditText.getText().toString().trim();

                if (!url.isEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {
                    webView.setVisibility(View.VISIBLE);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.loadUrl(url);
                }
            }
        });
    }
}