package com.milo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.milo.widget.TouchView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    Button btnTouch;
    TouchView touchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btnTouch = findViewById(R.id.btn_touch);
        btnTouch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String channel = PackageUtils.getMetaValue(getBaseContext(), "UMENG_CHANNEL");
                Toast.makeText(getBaseContext(), channel, Toast.LENGTH_SHORT).show();
            }
        });

        touchView = findViewById(R.id.touchview);
        touchView.post(new Runnable() {
            @Override
            public void run() {
                touchView.setLimitAuto((ViewGroup) findViewById(R.id.lay_root));
            }
        });
        touchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "touchView click .. ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
