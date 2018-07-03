package com.eyedog.piececode.textSticky.switcher;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.eyedog.piececode.textSticky.textRes.AlignEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;

/**
 * created by jw200 at 2018/7/2 18:58
 **/
public class AlignSwitcher extends BaseSwitcher<AlignEnum> {
    public AlignSwitcher(Context context) {
        super(context);
    }

    public AlignSwitcher(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AlignSwitcher(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected AlignEnum[] getSwitchImages() {
        return ResList.ALIGN_ENUMS;
    }
}
