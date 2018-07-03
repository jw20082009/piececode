package com.eyedog.piececode.textSticky.switcher;

import com.eyedog.piececode.textSticky.textRes.ISwitchRes;

/**
 * created by jw200 at 2018/7/2 18:44
 **/
public interface ISwitchListener<T extends ISwitchRes> {
    void onSwitched(int position, T res);
}
