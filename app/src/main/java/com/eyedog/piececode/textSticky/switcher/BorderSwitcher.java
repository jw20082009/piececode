package com.eyedog.piececode.textSticky.switcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.eyedog.piececode.textSticky.textRes.BorderEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;

/**
 * created by jw200 at 2018/7/2 18:55
 **/
public class BorderSwitcher extends BaseSwitcher<BorderEnum> {

    public BorderSwitcher(Context context) {
        super(context);
    }

    public BorderSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected BorderEnum[] getSwitchImages() {
        return ResList.BORDER_ENUMS;
    }
}
