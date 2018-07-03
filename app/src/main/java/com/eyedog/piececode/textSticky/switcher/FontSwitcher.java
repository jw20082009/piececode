package com.eyedog.piececode.textSticky.switcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.eyedog.piececode.textSticky.textRes.FontEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;

/**
 * created by jw200 at 2018/7/2 18:39
 **/
public class FontSwitcher extends BaseSwitcher<FontEnum> {

    public FontSwitcher(Context context) {
        super(context);
    }

    public FontSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FontSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected FontEnum[] getSwitchImages() {
        return ResList.FONT_ENUMS;
    }
}
