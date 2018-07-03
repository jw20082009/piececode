package com.eyedog.piececode.textSticky.textRes;

import com.eyedog.piececode.R;

/**
 * created by jw200 at 2018/7/3 18:08
 **/
public enum BorderEnum implements ISwitchRes {

    NONE(R.drawable.btn_camera_edit_addtext_bg_none), SOLID(
        R.drawable.btn_camera_edit_addtext_bg_solid), STROKEN(
        R.drawable.btn_camera_edit_addtext_bg_border);

    int res;

    BorderEnum(int res) {
        this.res = res;
    }

    @Override
    public int getRes() {
        return res;
    }
}
