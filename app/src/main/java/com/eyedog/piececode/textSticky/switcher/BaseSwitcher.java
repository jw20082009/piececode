package com.eyedog.piececode.textSticky.switcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.eyedog.piececode.R;
import com.eyedog.piececode.textSticky.textRes.ISwitchRes;

/**
 * created by jw200 at 2018/7/2 18:40
 **/
public class BaseSwitcher<T extends ISwitchRes> extends ImageView implements View.OnClickListener {

    protected T[] mImages;

    protected int currentPosition = 0;

    private ISwitchListener<T> mSwitchListener;

    public BaseSwitcher(Context context) {
        super(context);
        init();
    }

    public BaseSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BaseSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public T getCurrentRes() {
        if (mImages == null) {
            mImages = getSwitchImages();
        }
        if (mImages != null && mImages.length > 0) {
            return mImages[currentPosition % mImages.length];
        }
        return null;
    }

    protected T[] getSwitchImages() {
        return null;
    }

    protected boolean needBackground() {
        return true;
    }

    protected void init() {
        setScaleType(ScaleType.CENTER);
        if (needBackground()) {
            setBackgroundResource(R.drawable.shape_04black_radius8);
        }
        setOnClickListener(this);
        currentPosition = 0;
        mImages = getSwitchImages();
        if (mImages != null && mImages.length > 0) {
            setImageResource(mImages[0].getRes());
            currentPosition = 0;
        }
    }

    public void setSwitchListener(ISwitchListener listener) {
        this.mSwitchListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mImages != null) {
            currentPosition = currentPosition + 1;
            T res = mImages[currentPosition % mImages.length];
            setImageResource(res.getRes());
            if (mSwitchListener != null) {
                mSwitchListener.onSwitched(currentPosition, res);
            }
        }
    }
}
