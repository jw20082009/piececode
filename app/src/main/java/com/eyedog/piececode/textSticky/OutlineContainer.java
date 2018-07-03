package com.eyedog.piececode.textSticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * created by jw200 at 2018/7/3 21:47
 **/
public class OutlineContainer extends ViewGroup {

    View child;
    private Paint mPaint;

    public OutlineContainer(Context context) {
        super(context);
        init();
    }

    public OutlineContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public OutlineContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setWillNotDraw(false);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int childCount = getChildCount();
        if (childCount > 1) {
            throw new RuntimeException("only support one child!");
        }
        child = getChildAt(0);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        child.measure(MeasureSpec.makeMeasureSpec(width, MeasureSpec.AT_MOST),
            MeasureSpec.makeMeasureSpec(height, MeasureSpec.AT_MOST));
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        if (child != null) {
            float left = getMeasuredWidth() / 2.0f - child.getMeasuredWidth() / 2.0f;
            float top = getMeasuredHeight() / 2.0f - child.getMeasuredHeight() / 2.0f;
            float right = left + child.getMeasuredWidth();
            float bottom = top + child.getMeasuredHeight();
            child.layout((int) left, (int) top, (int) right, (int) bottom);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Rect rect = new Rect();
        //child.getHitRect(rect);
        //RectF rectF = new RectF(rect);
        //canvas.drawRoundRect(rectF, 30, 30, mPaint);
    }
}
