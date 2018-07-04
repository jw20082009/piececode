package com.eyedog.piececode.textSticky;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.eyedog.piececode.R;
import com.eyedog.piececode.textSticky.switcher.AlignSwitcher;
import com.eyedog.piececode.textSticky.switcher.BorderSwitcher;
import com.eyedog.piececode.textSticky.switcher.FontSwitcher;
import com.eyedog.piececode.textSticky.switcher.ISwitchListener;
import com.eyedog.piececode.textSticky.textRes.AlignEnum;
import com.eyedog.piececode.textSticky.textRes.BorderEnum;
import com.eyedog.piececode.textSticky.textRes.FontEnum;
import com.eyedog.piececode.utils.SoftKeyboardHelper;

/**
 * created by jw200 at 2018/7/3 18:16
 **/
public class StickyTextPanel extends FrameLayout
    implements SoftKeyboardHelper.OnKeyboardStateListener {

    private StickyEditText mTextInput;

    private LinearLayout mTextInputBar;

    private SoftKeyboardHelper mKeyBoardHelper;

    private FontSwitcher mFontSwitcher;

    private BorderSwitcher mBorderSwitcher;

    private AlignSwitcher mAlignSwitcher;

    public StickyTextPanel(@NonNull Context context) {
        super(context);
        init();
    }

    public StickyTextPanel(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyTextPanel(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.eye_text_sticky_panel, this);
        mTextInput = findViewById(R.id.et_text_input);
        mTextInputBar = findViewById(R.id.ll_text_input_bar);
        mFontSwitcher = findViewById(R.id.font_switcher);
        mFontSwitcher.setSwitchListener(mFontListener);
        mBorderSwitcher = findViewById(R.id.border_switcher);
        mBorderSwitcher.setSwitchListener(mBorderListener);
        mAlignSwitcher = findViewById(R.id.align_switcher);
        mAlignSwitcher.setSwitchListener(mAlignListener);
        mTextInput.setTypeface(
            Typeface.createFromAsset(getContext().getAssets(), mFontSwitcher.getCurrentRes().res));
        mKeyBoardHelper = new SoftKeyboardHelper(getContext(), this);
    }

    /**
     * 开始编辑api
     */
    public void edit() {
        mTextInput.setVisibility(View.VISIBLE);
        mTextInput.setSelection(0);
        mTextInput.requestFocus();
        mTextInput.setAlpha(0f);
        showSoftInput(mTextInput);
    }

    private void showSoftInput(View view) {
        if (view != null && view.getContext() != null) {
            InputMethodManager imm =
                (InputMethodManager) view.getContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
            }
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mKeyBoardHelper != null) {
            mKeyBoardHelper.listen();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mKeyBoardHelper != null) {
            mKeyBoardHelper.cancel();
        }
    }

    @Override
    public void onStateChanged(boolean shown, int height) {
        if (getHeight() > 0) {
            if (shown) {
                mTextInputBar.setTranslationY(-1 * height);
                mTextInputBar.setVisibility(View.VISIBLE);
                mTextInputBar.setAlpha(0f);
                mTextInputBar.animate().alpha(1.0f).setListener(null);
                mTextInput.setTranslationY(
                    (getHeight() - height) / 2.0f
                        - mTextInput.getHeight() / 2.0f);
                mTextInput.setVisibility(View.VISIBLE);
                mTextInput.setAlpha(1.0f);
            } else {
                mTextInput.setVisibility(View.GONE);
                mTextInput.setTranslationY(0);
                mTextInput.setAlpha(0f);
                mTextInputBar.setTranslationY(0);
                mTextInputBar.setAlpha(1.0f);
                mTextInputBar.setVisibility(View.GONE);
                mTextInputBar.setAlpha(0f);
            }
        }
    }

    ISwitchListener<FontEnum> mFontListener = new ISwitchListener<FontEnum>() {
        @Override
        public void onSwitched(int position, FontEnum res) {
            mTextInput.setTypeface(
                Typeface.createFromAsset(getContext().getAssets(),
                    mFontSwitcher.getCurrentRes().res));
        }
    };

    ISwitchListener<BorderEnum> mBorderListener = new ISwitchListener<BorderEnum>() {
        @Override
        public void onSwitched(int position, BorderEnum res) {
            mTextInput.setBorderMode(res);
        }
    };

    ISwitchListener<AlignEnum> mAlignListener = new ISwitchListener<AlignEnum>() {
        @Override
        public void onSwitched(int position, AlignEnum res) {
            switch (res) {
                case LEFT:
                    mTextInput.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    break;
                case RIGHT:
                    mTextInput.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    break;
                default:
                    mTextInput.setGravity(Gravity.CENTER);
                    break;
            }
        }
    };
}
