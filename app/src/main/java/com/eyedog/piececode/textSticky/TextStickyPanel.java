package com.eyedog.piececode.textSticky;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.eyedog.piececode.R;
import com.eyedog.piececode.textSticky.switcher.FontSwitcher;
import com.eyedog.piececode.textSticky.switcher.ISwitchListener;
import com.eyedog.piececode.textSticky.textRes.FontEnum;
import com.eyedog.piececode.utils.SoftKeyboardHelper;

/**
 * created by jw200 at 2018/7/3 18:16
 **/
public class TextStickyPanel extends FrameLayout
    implements SoftKeyboardHelper.OnKeyboardStateListener {

    private OutlineContainer mTextInputContainer;

    private EditText mTextInput;

    private LinearLayout mTextInputBar;

    private SoftKeyboardHelper mKeyBoardHelper;

    private FontSwitcher mFontSwitcher;

    public TextStickyPanel(@NonNull Context context) {
        super(context);
        init();
    }

    public TextStickyPanel(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextStickyPanel(@NonNull Context context, @Nullable AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.eye_text_sticky_panel, this);
        mTextInputContainer = findViewById(R.id.et_text_input_container);
        mTextInput = findViewById(R.id.et_text_input);
        mTextInputBar = findViewById(R.id.ll_text_input_bar);
        mFontSwitcher = findViewById(R.id.font_switcher);
        mFontSwitcher.setSwitchListener(fontListener);
        mTextInput.setTypeface(
            Typeface.createFromAsset(getContext().getAssets(), mFontSwitcher.getCurrentRes().res));
        mKeyBoardHelper = new SoftKeyboardHelper(getContext(), this);
    }

    /**
     * 开始编辑api
     */
    public void edit() {
        mTextInputContainer.setVisibility(View.VISIBLE);
        mTextInput.setSelection(0);
        mTextInput.requestFocus();
        mTextInputContainer.setAlpha(0f);
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
                mTextInputContainer.setTranslationY(
                    (getHeight() - height) / 2.0f
                        - mTextInputContainer.getHeight() / 2.0f);
                mTextInputContainer.setVisibility(View.VISIBLE);
                mTextInputContainer.setAlpha(1.0f);
            } else {
                mTextInputContainer.setVisibility(View.GONE);
                mTextInputContainer.setTranslationY(0);
                mTextInputContainer.setAlpha(0f);
                mTextInputBar.setTranslationY(0);
                mTextInputBar.setAlpha(1.0f);
                mTextInputBar.setVisibility(View.GONE);
                mTextInputBar.setAlpha(0f);
            }
        }
    }

    ISwitchListener<FontEnum> fontListener = new ISwitchListener<FontEnum>() {
        @Override
        public void onSwitched(int position, FontEnum res) {
            mTextInput.setTypeface(
                Typeface.createFromAsset(getContext().getAssets(),
                    mFontSwitcher.getCurrentRes().res));
        }
    };
}
