package com.jhems.explicitintent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        TextView txtMessage = findViewById(R.id.txtMessage);
        Button btnBackToMainActivity = findViewById(R.id.btnBackToMainActivity);

        Intent intent = getIntent();
        if (intent != null) {
            String message = intent.getStringExtra("key");
            if (message != null) {
                txtMessage.setText(message);
            }
        }

        btnBackToMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}