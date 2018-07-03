package com.eyedog.piececode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.eyedog.piececode.textSticky.TextStickyPanel;

public class TextStickerActivity extends AppCompatActivity implements View.OnClickListener {

    private Button mEdit;

    private TextStickyPanel mPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_sticker);
        mEdit = findViewById(R.id.btn_editmode);
        mEdit.setOnClickListener(this);
        mPanel = findViewById(R.id.text_sticky_panel);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_editmode:
                mPanel.edit();
                break;
        }
    }
}
