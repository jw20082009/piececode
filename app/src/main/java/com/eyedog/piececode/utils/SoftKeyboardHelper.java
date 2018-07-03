package com.eyedog.piececode.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.eyedog.piececode.R;

/**
 * created by jw200 at 2018/7/3 16:31
 **/
public class SoftKeyboardHelper {
    private static final String TAG = "SoftKeyboardHelper";
    private final AnchorDialog mDialog;
    private final Handler mHandler;

    public SoftKeyboardHelper(Context activity, OnKeyboardStateListener listener) {
        mHandler = new Handler(Looper.getMainLooper());
        mDialog = new AnchorDialog(activity, listener);
    }

    public void listen() {
        if (!mDialog.isShowing()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    mDialog.show();
                }
            });
        }
    }

    public void cancel() {
        if (mDialog.isShowing()) {
            mDialog.cancel();
        }
    }

    public interface OnKeyboardStateListener {
        void onStateChanged(boolean shown, int height);
    }

    private class AnchorDialog extends Dialog {
        private final Rect mWindowRect = new Rect();
        private final OnKeyboardStateListener mListener;
        private final int mMinPosChangeValue;

        public AnchorDialog(@NonNull Context context, OnKeyboardStateListener listener) {
            super(context, R.style.SoftInputDialog);
            this.mListener = listener;
            mMinPosChangeValue = context.getResources().getDisplayMetrics().heightPixels / 5;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            getWindow().getDecorView().addOnLayoutChangeListener(mLayoutChangedListener);
        }

        @Override
        protected void onStart() {
            super.onStart();
            Window window = getWindow();
            if (window != null) {
                window.setBackgroundDrawable(null);
                WindowManager.LayoutParams params = window.getAttributes();
                params.width = 1;//px
                params.height = WindowManager.LayoutParams.MATCH_PARENT;//软键盘弹出时会压缩高度，引起布局变化
                params.gravity = Gravity.END | Gravity.BOTTOM;
                params.dimAmount = 0.0f;
                params.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
                params.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                params.flags |= WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM;
                //params.flags |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
                params.windowAnimations = 0;
                window.setAttributes(params);
            }
        }

        private final View.OnLayoutChangeListener mLayoutChangedListener =
            new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom,
                    int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    if (getWindow() == null) return;
                    WindowManager.LayoutParams params = getWindow().getAttributes();
                    boolean isTranslucentStatus =
                        (params.flags & WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS) != 0;
                    final int oldHeight;
                    final int currHeight;
                    if (isTranslucentStatus) {
                        oldHeight = mWindowRect.bottom - mWindowRect.top;
                        View decorView = getWindow().getDecorView();
                        decorView.getWindowVisibleDisplayFrame(mWindowRect);
                        currHeight = mWindowRect.bottom - mWindowRect.top;
                    } else {
                        oldHeight = oldBottom - oldTop;
                        currHeight = bottom - top;
                    }
                    final int changeHeight = Math.abs(oldHeight - currHeight);
                    //SLog.d(TAG,"onLayoutChange isTranslucentStatus = %s,oldHeight = %s,currHeight = %s,changeHeight = %s",isTranslucentStatus,oldHeight,currHeight,changeHeight);
                    if (oldHeight > 0 && changeHeight > mMinPosChangeValue) {
                        boolean shown = currHeight < oldHeight;
                        mListener.onStateChanged(shown, changeHeight);
                    }
                }
            };
    }
}
