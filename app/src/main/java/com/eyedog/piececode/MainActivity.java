package com.eyedog.piececode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.eyedog.piececode.widgets.TransitionView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    TransitionView transitionView;
    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transitionView = findViewById(R.id.transitionview);
        transitionView.setOnTouchListener(this);
        tips = findViewById(R.id.tips);
    }

    boolean isExpand = false;

    public void expand(View view) {
        if (!isExpand) {
            transitionView.scale01();
            isExpand = true;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (isExpand) {
                    transitionView.scale10();
                    isExpand = false;
                }
                break;
        }
        return false;
    }

    public void subClick(View view) {
        if (view instanceof TextView) {
            TextView textView = (TextView) view;
            String text = textView.getText().toString();
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
            tips.setText(text);
        }
    }
}
