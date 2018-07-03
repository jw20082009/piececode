package com.eyedog.piececode.textSticky.switcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.eyedog.piececode.textSticky.textRes.ColorEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;

/**
 * created by jw200 at 2018/7/2 19:24
 **/
public class ColorSwitcher extends BaseSwitcher<ColorEnum> {

    public ColorSwitcher(Context context) {
        super(context);
    }

    public ColorSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected boolean needBackground() {
        return false;
    }

    @Override
    protected ColorEnum[] getSwitchImages() {
        return ResList.COLOR_ENUMS;
    }
}
