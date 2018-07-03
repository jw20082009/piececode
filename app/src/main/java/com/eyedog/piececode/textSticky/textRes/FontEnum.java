package com.eyedog.piececode.textSticky.textRes;

import com.eyedog.piececode.R;

/**
 * created by jw200 at 2018/7/3 17:31
 **/
public enum FontEnum implements ISwitchRes {

    MODERN("现代", R.drawable.btn_camera_edit_addtext_fontfamily_modern, "han_sans_cn_heavy.otf"),
    YOUNG("青年", R.drawable.btn_camera_edit_addtext_fontfamily_young, "fontfamily_young.otf"),
    CUTE("漫语", R.drawable.btn_camera_edit_addtext_fontfamily_cute, "fontfamily_cute.ttf"),
    OLD_SCHOOL("明朝", R.drawable.btn_camera_edit_addtext_fontfamily_oldschool,
        "fontfamily_oldschool.otf");

    public String name, res;
    public int icon;

    @Override
    public int getRes() {
        return icon;
    }

    FontEnum(String name, int icon, String res) {
        this.name = name;
        this.res = res;
        this.icon = icon;
    }

}
