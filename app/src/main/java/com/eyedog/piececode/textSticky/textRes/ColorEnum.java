package com.eyedog.piececode.textSticky.textRes;

import android.graphics.Color;
import com.eyedog.piececode.R;

/**
 * created by jw200 at 2018/7/3 18:04
 **/
public enum ColorEnum implements ISwitchRes {
    SELECT(R.drawable.btn_camera_edit_addtext_getcolor), PICK(
        R.drawable.btn_camera_edit_addtext_getcolor_black);

    public int res, color = Color.WHITE;

    @Override
    public int getRes() {
        return res;
    }

    ColorEnum(int res) {
        this.res = res;
    }
}
