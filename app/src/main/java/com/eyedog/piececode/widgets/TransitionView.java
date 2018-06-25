
package com.eyedog.piececode.widgets;

import android.animation.Animator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * 封装了第一个子控件和最后一个子空间之间的切换动画 created by jw200 at 2018/6/2 18:51
 **/
public class TransitionView extends FrameLayout {
    View child0, child1, viewToDraw;
    ScaleEntity mAnimEntity;
    boolean isScaling = false;
    private final int ANIMA_DURATION = 300;
    private boolean needAnimate = true;

    public TransitionView(@NonNull Context context) {
        this(context, null);
    }

    public TransitionView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransitionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TransitionView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setWillNotDraw(false);
    }

    public void scale(final View startView, final View endView) {
        if (needAnimate) {
            TransitionView.this.isScaling = true;
            startView.setVisibility(VISIBLE);
            startView.setAlpha(1.0f);
            endView.setVisibility(VISIBLE);
            endView.setAlpha(0f);

            int widthStart = startView.getWidth();
            int heightStart = startView.getHeight();
            int widthEnd = endView.getWidth();
            int heightEnd = endView.getHeight();

            ScaleEntity start = new ScaleEntity();
            if (widthStart * heightStart < widthEnd * heightEnd) {
                viewToDraw = endView;
            } else {
                viewToDraw = startView;
            }
            start.sx = viewToDraw == startView ? 1.0f : (1.0f * widthStart / widthEnd);
            start.sy = viewToDraw == startView ? 1.0f : (1.0f * heightStart / heightEnd);

            //calculate px
            float scale = viewToDraw == startView ? (1.0f * widthStart / widthEnd) : (1.0f * widthEnd / widthStart);
            int offset = viewToDraw == startView ? (startView.getLeft() - endView.getLeft()) : (endView.getLeft() - startView.getLeft());
            start.px = scale * offset / (1 - scale);

            //calculate py
            float scaley = viewToDraw == startView ? (1.0f * heightStart / heightEnd) : (1.0f * heightEnd / heightStart);
            int offsety = viewToDraw == startView ? (startView.getTop() - endView.getTop()) : (endView.getTop() - startView.getTop());
            start.py = scaley * offsety / (1 - scaley);

            ScaleEntity end = new ScaleEntity();
            end.sx = viewToDraw == startView ? (1.0f * widthEnd / widthStart) : 1.0f;
            end.sy = viewToDraw == startView ? (1.0f * heightEnd / heightStart) : 1.0f;
            end.px = start.px;
            end.py = start.py;

            ValueAnimator scaleAnimator = ValueAnimator.ofObject(new ScaleEvaluator(), start, end);
            scaleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    mAnimEntity = (ScaleEntity) animation.getAnimatedValue();
                    invalidate();
                }
            });

            scaleAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                    startView.setVisibility(GONE);
                    startView.setAlpha(0f);
                    isScaling = true;
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    endView.setVisibility(VISIBLE);
                    endView.setAlpha(1.0f);
                    isScaling = false;
                    invalidate();//clear screen
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    endView.setVisibility(VISIBLE);
                    endView.setAlpha(1.0f);
                    isScaling = false;
                    invalidate();//clear screen
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
            scaleAnimator.setDuration(ANIMA_DURATION);
            scaleAnimator.start();
        } else {
            endView.setAlpha(1.0f);
            endView.setVisibility(VISIBLE);
            startView.setAlpha(0f);
            startView.setVisibility(GONE);
            TransitionView.this.isScaling = false;
        }
    }

    public void scale01() {
        scale(child0, child1);
    }

    public void scale10() {
        scale(child1, child0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (getChildCount() > 1 && (child0 == null || child1 == null)) {
            child0 = getChildAt(0);
            child1 = getChildAt(getChildCount() - 1);
        }
    }

    class ScaleEvaluator implements TypeEvaluator<ScaleEntity> {

        @Override
        public ScaleEntity evaluate(float fraction, ScaleEntity startValue, ScaleEntity endValue) {
            ScaleEntity animEntity = new ScaleEntity();
            animEntity.sx = startValue.sx + fraction * (endValue.sx - startValue.sx);
            animEntity.sy = startValue.sy + fraction * (endValue.sy - startValue.sy);
            animEntity.px = startValue.px + fraction * (endValue.px - startValue.px);
            animEntity.py = startValue.py + fraction * (endValue.py - startValue.py);
            return animEntity;
        }
    }

    class ScaleEntity {
        float px, py, sx, sy;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAnimEntity != null && isScaling && viewToDraw != null) {
            canvas.save();
            canvas.translate(viewToDraw.getLeft(), viewToDraw.getTop());
            canvas.scale(mAnimEntity.sx, mAnimEntity.sy, mAnimEntity.px, mAnimEntity.py);
            viewToDraw.getBackground().draw(canvas);
            canvas.restore();
        } else {
            super.onDraw(canvas);
        }
    }
}