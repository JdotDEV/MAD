package com.jhems.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        toast = Toast.makeText(this, "Second_onCreate", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Second_onStart", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Second_onResume", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "Second_onPause", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "Second_onStop", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "Second_onRestart", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (toast != null) {
            toast.cancel(); // Dismiss the toast to avoid potential memory leaks
        }
        Toast.makeText(this, "Second_onDestroy", Toast.LENGTH_SHORT).show();
        toast.setGravity(Gravity.CENTER, 0, 0);
    }
}
