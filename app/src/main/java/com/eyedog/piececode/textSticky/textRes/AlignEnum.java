package com.eyedog.piececode.textSticky.textRes;

import com.eyedog.piececode.R;

/**
 * created by jw200 at 2018/7/3 17:58
 **/
public enum AlignEnum implements ISwitchRes {
    LEFT(R.drawable.btn_camera_edit_addtext_align_left), CENTER(
        R.drawable.btn_camera_edit_addtext_align_center), RIGHT(
        R.drawable.btn_camera_edit_addtext_align_right);

    int res;

    @Override
    public int getRes() {
        return res;
    }

    AlignEnum(int res) {
        this.res = res;
    }
}
