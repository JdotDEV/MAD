package com.jhems.explicitintent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOpenSecondActivity = findViewById(R.id.btnOpenSecondActivity);

        btnOpenSecondActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an explicit intent to open SecondActivity
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);

                // Optionally, you can pass data to the next activity
                intent.putExtra("key", "Hello from MainActivity!");

                // Start the activity
                startActivity(intent);
            }
        });
    }
}