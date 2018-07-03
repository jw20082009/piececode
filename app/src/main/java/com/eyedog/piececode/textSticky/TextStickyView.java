package com.eyedog.piececode.textSticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.eyedog.piececode.EyeApplication;
import com.eyedog.piececode.textSticky.textRes.AlignEnum;
import com.eyedog.piececode.textSticky.textRes.BorderEnum;
import com.eyedog.piececode.textSticky.textRes.ColorEnum;
import com.eyedog.piececode.textSticky.textRes.FontEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;
import com.eyedog.piececode.utils.DensityUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * created by jw200 at 2018/7/3 15:42
 **/
public class TextStickyView extends View {
    public static final float TEXT_SIZE_DEFAULT = DensityUtil.dip2px(EyeApplication.instance, 20);
    private Paint mHelpPaint;
    private TextPaint mPaint;
    private String mText;
    private List<String> mTextContents;
    private static final int BORDER_RADIUS = DensityUtil.dip2px(EyeApplication.instance, 10);
    private BorderEnum mBorderEnum = ResList.BORDER_ENUMS[0];
    private FontEnum mFontEnum = ResList.FONT_ENUMS[0];
    private ColorEnum mColorEnum = ResList.COLOR_ENUMS[0];
    private AlignEnum mAlignEnum = ResList.ALIGN_ENUMS[0];
    private int mMeasureWidth, mMeasureHeight;

    public TextStickyView(Context context) {
        super(context);
        init();
    }

    public TextStickyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TextStickyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new TextPaint();
        mPaint.setColor(Color.WHITE);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(TEXT_SIZE_DEFAULT);
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.LEFT);
        mPaint.setTypeface(Typeface.createFromAsset(getContext().getAssets(), mFontEnum.res));
    }

    /**
     * @param color Color.BLACK
     * @param style Paint.Style.STROKE
     */
    public void setupBorderPaint(int color, Paint.Style style) {
        if (mHelpPaint == null) {
            mHelpPaint = new Paint();
        }
        mHelpPaint.setColor(color);
        mHelpPaint.setStyle(style);
        mHelpPaint.setAntiAlias(true);
        mHelpPaint.setStrokeWidth(4);
    }

    public void setText(String text) {
        if (!TextUtils.equals(text, mText)) {
            this.mText = text;
            parseText();
            invalidate();
        }
    }

    public void setTextColor(int newColor) {
        mPaint.setColor(newColor);
        invalidate();
    }

    public void setBorderColor(int newColor) {
        if (mHelpPaint == null) {
            setTextColor(newColor);
        } else {

        }
    }

    private void parseText() {
        if (TextUtils.isEmpty(mText)) {
            return;
        }
        if (mTextContents == null) {
            mTextContents = new ArrayList<>();
        }
        mTextContents.clear();
        String[] splits = mText.split("\n");
        for (String text : splits) {
            mTextContents.add(text);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mMeasureWidth = getMeasuredWidth();
        mMeasureHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBorder(canvas);
        drawText(canvas);
    }

    private void drawText(Canvas canvas) {
        if (mTextContents != null && mTextContents.size() > 0) {
            Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
            int charMinHeight = Math.abs(fontMetrics.top) + Math.abs(fontMetrics.bottom);
            int y = 0, lastWidth = mMeasureWidth - getPaddingLeft() - getPaddingRight();
            float lastLeft = getPaddingLeft();
            int textHeight = fontMetrics.bottom - fontMetrics.top;
            for (String text : mTextContents) {
                Rect textRect = new Rect();
                mPaint.getTextBounds(text, 0, text.length(), textRect);
                if (mAlignEnum == AlignEnum.CENTER) {
                    int width = textRect.width();
                    if (width > lastWidth) {
                        float x = lastLeft - (width - lastWidth) / 2.0f;
                        canvas.drawText(text, x,
                            y = y + textHeight, mPaint);
                        lastWidth = textRect.width();
                        lastLeft = x;
                    } else {
                        float x = lastLeft + (lastWidth - width) / 2.0f;
                        canvas.drawText(text, x, y = y + textHeight, mPaint);
                        lastWidth = textRect.width();
                        lastLeft = x;
                    }
                } else if (mAlignEnum == AlignEnum.RIGHT) {
                    float x = lastLeft + lastWidth - textRect.width();
                    canvas.drawText(text, x, y = y + textHeight, mPaint);
                    lastWidth = textRect.width();
                    lastLeft = x;
                } else {
                    canvas.drawText(text, 0, y = y + textHeight, mPaint);
                    lastWidth = textRect.width();
                    lastLeft = 0;
                }
            }
        }
    }

    private void drawBorder(Canvas canvas) {
        if (mBorderEnum != BorderEnum.NONE && mTextContents != null && mTextContents.size() > 0) {

        }
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
