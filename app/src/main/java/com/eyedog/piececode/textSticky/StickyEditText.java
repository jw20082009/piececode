package com.eyedog.piececode.textSticky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import com.eyedog.piececode.textSticky.textRes.AlignEnum;
import com.eyedog.piececode.textSticky.textRes.BorderEnum;
import com.eyedog.piececode.textSticky.textRes.ColorEnum;
import com.eyedog.piececode.textSticky.textRes.FontEnum;
import com.eyedog.piececode.textSticky.textRes.ResList;
import java.util.ArrayList;
import java.util.List;

/**
 * 当输入数字或者字母导致换行时会出现行数计算出错，从而边框绘制不正确问题
 * created by jw200 at 2018/7/4 11:25
 **/
public class StickyEditText extends EditText {

    Paint mBorderPaint, mXPaint;

    List<String> mRows = new ArrayList<>();

    final int STROKEN_WIDTH = 10;

    final int RADIUS = 20;

    String mText;

    private BorderEnum mBorderEnum = ResList.BORDER_ENUMS[0];
    private FontEnum mFontEnum = ResList.FONT_ENUMS[0];
    private ColorEnum mColorEnum = ResList.COLOR_ENUMS[0];
    private AlignEnum mAlignEnum = ResList.ALIGN_ENUMS[0];

    public StickyEditText(Context context) {
        super(context);
        init();
    }

    public StickyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StickyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(STROKEN_WIDTH);
        mBorderPaint.setStyle(Paint.Style.STROKE);

        mXPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mXPaint.setColor(Color.WHITE);
        mXPaint.setStrokeWidth(STROKEN_WIDTH);
        mXPaint.setStyle(Paint.Style.STROKE);
        mXPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setBorderMode(BorderEnum borderMode) {
        this.mBorderEnum = borderMode;
        if (mBorderEnum == BorderEnum.STROKEN) {
            mBorderPaint.setStyle(Paint.Style.STROKE);
            mXPaint.setStyle(Paint.Style.STROKE);
            mXPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        } else if (mBorderEnum == BorderEnum.SOLID) {
            mBorderPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mXPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mXPaint.setXfermode(null);
        }
        invalidate();
    }

    private void parseText(String text) {
        if (!TextUtils.equals(text, mText)) {
            mText = text;
            mRows.clear();
            if (!TextUtils.isEmpty(text)) {
                String[] strs = text.split("\\n");
                for (String str : strs) {
                    parseRows(str);
                }
                if (text.endsWith("\n")) {
                    mRows.add(" ");
                }
            }
        }
    }

    private void parseRows(String row) {
        if (!TextUtils.isEmpty(row)) {
            Rect rect = new Rect();
            getPaint().getTextBounds(row, 0, row.length(), rect);
            int length = rect.width();
            int viewWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight();
            if (length < viewWidth) {
                mRows.add(row);
            } else {
                int start = 0;
                for (int i = 0; i < row.length(); i++) {
                    getPaint().getTextBounds(row, start < 0 ? 0 : start,
                        i + 1 > row.length() ? row.length() : i + 1, rect);
                    if (rect.width() > viewWidth) {
                        mRows.add(row.substring(start, i));
                        start = i;
                    }
                    if (i == row.length() - 1) {
                        mRows.add(row.substring(start, row.length()));
                    }
                }
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        String text = getText().toString();
        if (mBorderEnum != BorderEnum.NONE) {
            parseText(text);
            Rect lastRect = null;
            for (int i = 0; i < mRows.size(); i++) {
                Rect strRect = new Rect();
                String rowStr = mRows.get(i);
                getPaint().getTextBounds(rowStr, 0, rowStr.length(), strRect);

                int left = getPaddingLeft(), right = getMeasuredWidth() - getPaddingRight();
                int gravity = getGravity();
                int top = (int) ((getMeasuredHeight() - getLineHeight() * mRows.size()) / 2.0f
                    + i * getLineHeight() - (i == 0 ? getPaddingTop() : 0));
                int bottom = top + getLineHeight() + (i == (mRows.size() - 1) ? getPaddingBottom()
                    : 0) + (i == 0 ? getPaddingTop() : 0);
                if (gravity == Gravity.CENTER) {
                    int width = strRect.width() + getPaddingLeft() + getPaddingRight();
                    int templeft = (int) ((getMeasuredWidth() - strRect.width()) / 2.0f
                        - getPaddingLeft());
                    if (lastRect != null && Math.abs(templeft - lastRect.left) < 20) {
                        templeft = lastRect.left;
                    }
                    if (templeft > left && Math.abs(templeft - left) > RADIUS * 1.5) {
                        left = templeft;
                    }
                    int tempRight = left + width;
                    if (lastRect != null && Math.abs(tempRight - lastRect.right) < 20) {
                        tempRight = lastRect.right;
                    }
                    if (tempRight < right && Math.abs(tempRight - right) > RADIUS * 1.5) {
                        right = tempRight;
                    }
                } else if (gravity == (Gravity.RIGHT | Gravity.CENTER_VERTICAL)) {
                    int templeft =
                        getMeasuredWidth() - getPaddingRight() - getPaddingLeft() - strRect.width();
                    if (lastRect != null && Math.abs(templeft - lastRect.left) < 20) {
                        templeft = lastRect.left;
                    }
                    if (templeft > left && Math.abs(templeft - left) > RADIUS * 1.5) {
                        left = templeft;
                    }
                    right = right + 20;
                } else {
                    int tempRight = getPaddingLeft() + strRect.width() + getPaddingRight();
                    if (lastRect != null && Math.abs(tempRight - lastRect.right) < 20) {
                        tempRight = lastRect.right;
                    }
                    if (tempRight < right && Math.abs(tempRight - right) > RADIUS * 1.5) {
                        right = tempRight;
                    }
                    left = left - 20;
                }
                Rect rect = new Rect(left, top, right, bottom);
                canvas.drawRoundRect(new RectF(rect), RADIUS, RADIUS, mBorderPaint);

                int leftRadius = RADIUS;
                int rightRadius = RADIUS;

                if (i != 0 && lastRect != null) {
                    //计算leftRadius
                    if (rect.left - lastRect.left > 2 * RADIUS
                        || lastRect.left - rect.left > 2 * RADIUS) {
                        leftRadius = RADIUS;
                    } else if (rect.left - lastRect.left > RADIUS) {
                        leftRadius = (rect.left - lastRect.left - RADIUS);
                    } else if (rect.left - lastRect.left > 0) {
                        leftRadius = (int) (((rect.left - lastRect.left) / 2.0f));
                        if (leftRadius < RADIUS) {
                            RectF rectTopLeft =
                                new RectF(lastRect.left, lastRect.bottom - 2 * RADIUS,
                                    lastRect.left + 2 * RADIUS, lastRect.bottom);
                            canvas.drawArc(rectTopLeft, 90, 90, false, mXPaint);
                            RectF reDrawTopLeft =
                                new RectF(lastRect.left, lastRect.bottom - 2 * leftRadius,
                                    lastRect.left + 2 * leftRadius, lastRect.bottom);
                            canvas.drawArc(reDrawTopLeft, 90, 90, false, mBorderPaint);
                            canvas.drawLine(lastRect.left, lastRect.bottom - RADIUS,
                                lastRect.left, lastRect.bottom - leftRadius,
                                mBorderPaint);
                        }
                    } else if (lastRect.left - rect.left > RADIUS) {
                        leftRadius = (lastRect.left - rect.left - RADIUS);
                    } else {
                        leftRadius = (int) ((lastRect.left - rect.left) / 2.0f);
                        if (leftRadius < RADIUS) {
                            RectF rectTopLeft =
                                new RectF(rect.left, rect.top,
                                    rect.left + 2 * RADIUS, rect.top + 2 * RADIUS);
                            canvas.drawArc(rectTopLeft, 180, 90, false, mXPaint);
                            RectF reDrawTopLeft =
                                new RectF(rect.left, rect.top,
                                    rect.left + 2 * leftRadius, rect.top + 2 * leftRadius);
                            canvas.drawArc(reDrawTopLeft, 180, 90, false, mBorderPaint);
                            canvas.drawLine(rect.left, rect.top + leftRadius,
                                rect.left, rect.top + RADIUS, mBorderPaint);
                        }
                    }

                    //计算rightRadius
                    if (lastRect.right - rect.right > 2 * RADIUS
                        || rect.right - lastRect.right > 2 * RADIUS) {
                        rightRadius = RADIUS;
                    } else if (lastRect.right - rect.right > RADIUS) {
                        rightRadius = (lastRect.right - rect.right - RADIUS);
                    } else if (lastRect.right - rect.right > 0) {
                        rightRadius = (int) Math.floor(((lastRect.right - rect.right) / 2.0f));
                        if (rightRadius < RADIUS) {
                            RectF rectTopRight =
                                new RectF(lastRect.right - 2 * RADIUS,
                                    lastRect.bottom - 2 * RADIUS,
                                    lastRect.right, lastRect.bottom);
                            canvas.drawArc(rectTopRight, 0, 90, false, mXPaint);
                            RectF reDrawTopRight =
                                new RectF(lastRect.right - 2 * rightRadius,
                                    lastRect.bottom - 2 * rightRadius,
                                    lastRect.right, lastRect.bottom);
                            canvas.drawArc(reDrawTopRight, 0, 90, false, mBorderPaint);
                            canvas.drawLine(lastRect.right, lastRect.bottom - RADIUS,
                                lastRect.right, lastRect.bottom - rightRadius,
                                mBorderPaint);
                        }
                    } else if (rect.right - lastRect.right > RADIUS) {
                        rightRadius = (rect.right - lastRect.right - RADIUS);
                    } else {
                        rightRadius = (int) Math.floor((rect.right - lastRect.right) / 2.0f);
                        if (rightRadius < RADIUS) {
                            RectF rectBottomRight =
                                new RectF(rect.right - 2 * RADIUS, rect.top,
                                    rect.right, rect.top + 2 * RADIUS);
                            canvas.drawArc(rectBottomRight, -90, 90, false, mXPaint);
                            RectF reDrawBottomRight =
                                new RectF(rect.right - 2 * RADIUS, rect.top,
                                    rect.right, rect.top + 2 * RADIUS);
                            canvas.drawArc(reDrawBottomRight, -90, 90, false, mBorderPaint);
                            canvas.drawLine(rect.right, rect.top + leftRadius,
                                rect.right, rect.top + RADIUS, mBorderPaint);
                        }
                    }

                    if (i != 0 && lastRect != null) {
                        canvas.drawLine(Math.max(lastRect.left, rect.left) - leftRadius, top,
                            Math.min(lastRect.right, rect.right) + rightRadius, top, mXPaint);
                    }
                    if (rect.left > lastRect.left) {
                        RectF rectLeft =
                            new RectF(left, top, left + 2 * RADIUS, top + 2 * RADIUS);
                        canvas.drawArc(rectLeft, -180, 90, false, mXPaint);

                        RectF reDrawLeft =
                            new RectF(left - 2 * leftRadius, top, left, top + 2 * leftRadius);
                        canvas.drawArc(reDrawLeft, -92, 94, false, mBorderPaint);
                        if (leftRadius < RADIUS) {
                            canvas.drawLine(rect.left, rect.top + leftRadius,
                                rect.left,
                                rect.top + RADIUS, mBorderPaint);
                        }
                    } else if (rect.left < lastRect.left) {
                        RectF rectLeft = new RectF(lastRect.left, lastRect.bottom - 2 * RADIUS,
                            lastRect.left + 2 * RADIUS, lastRect.bottom);
                        canvas.drawArc(rectLeft, 90, 90, false, mXPaint);

                        RectF reDrawLeft =
                            new RectF(lastRect.left - 2 * leftRadius,
                                lastRect.bottom - 2 * leftRadius,
                                lastRect.left, lastRect.bottom);
                        canvas.drawArc(reDrawLeft, -2, 94, false, mBorderPaint);
                        if (leftRadius < RADIUS) {
                            canvas.drawLine(lastRect.left, lastRect.bottom - RADIUS,
                                lastRect.left, lastRect.bottom - leftRadius, mBorderPaint);
                        }
                    } else {
                        RectF topLeft = new RectF(lastRect.left, lastRect.bottom - 2 * RADIUS,
                            lastRect.left + 2 * RADIUS, lastRect.bottom);
                        canvas.drawArc(topLeft, 90, 90, false, mXPaint);
                        RectF bottomLeft =
                            new RectF(left, top, left + 2 * RADIUS, top + 2 * RADIUS);
                        canvas.drawArc(bottomLeft, 180, 90, false, mXPaint);
                        canvas.drawLine(left, top - RADIUS, left, top + RADIUS, mBorderPaint);
                    }

                    if (rect.right < lastRect.right) {
                        RectF rectRight =
                            new RectF(right - 2 * RADIUS, top, right, top + 2 * RADIUS);
                        canvas.drawArc(rectRight, -90, 90, false, mXPaint);
                        RectF reDrawRight =
                            new RectF(right, top, right + rightRadius * 2,
                                top + 2 * rightRadius);
                        canvas.drawArc(reDrawRight, -180, 90, false, mBorderPaint);
                        if (rightRadius < RADIUS) {
                            canvas.drawLine(right, top + rightRadius, right, top + RADIUS,
                                mBorderPaint);
                        }
                    } else if (rect.right > lastRect.right) {
                        RectF rectRight =
                            new RectF(lastRect.right - 2 * RADIUS, lastRect.bottom - 2 * RADIUS,
                                lastRect.right,
                                lastRect.bottom);
                        canvas.drawArc(rectRight, 0, 90, false, mXPaint);
                        RectF reDrawRight =
                            new RectF(lastRect.right, lastRect.bottom - 2 * rightRadius,
                                lastRect.right + 2 * rightRadius,
                                lastRect.bottom);
                        canvas.drawArc(reDrawRight, 90, 94, false, mBorderPaint);
                        if (rightRadius < RADIUS) {
                            canvas.drawLine(lastRect.right, lastRect.bottom - RADIUS,
                                lastRect.right, lastRect.bottom - rightRadius, mBorderPaint);
                        }
                    } else {
                        RectF topRight =
                            new RectF(lastRect.right - 2 * RADIUS,
                                lastRect.bottom - 2 * RADIUS,
                                lastRect.right, lastRect.bottom);
                        canvas.drawArc(topRight, 0, 90, false, mXPaint);

                        RectF bottomRight =
                            new RectF(right - 2 * RADIUS, top, right, top + 2 * RADIUS);
                        canvas.drawArc(bottomRight, -90, 90, false, mXPaint);
                        canvas.drawLine(right, top - RADIUS, right, top + RADIUS,
                            mBorderPaint);
                    }
                }
                lastRect = rect;
            }
        }
        super.onDraw(canvas);
    }
}
