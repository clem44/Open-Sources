package com.codeogenic.view;

import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Checkable;

import com.codeogenic.opensources.R;


/**
 * Created by clem_gumbs on 2/2/17.
 *
 *Copyright 2016 Clemaurde Gumbs
 *
     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.

 *
 */

public class SwellCheckBox extends View implements Checkable {

    private static final int DEFAULT_CHECKED_COLOR = Color.RED;
    private static final int DEFAULT_UNCHECK_COLOR = Color.GRAY;
    private static final int DEFAULT_ANIM_DURATION = 150;
    private static final int DEFAULT_TICK_WIDTH = 4;
    private static final int DEFAULT_TICK_COLOR = Color.WHITE;
    private static final boolean DEFAULT_HIDE_TICK = false;
    private static final String KEY_INSTANCE_STATE = "checkedInstance";
    private static final int DEFAULT_STYLE = 0;
    private static int DEFAULT_RADIUS = 10;
    private static final int DEFAULT_CORNER_RADIUS = 6;

    private static String TAG = "SwellCheckBox.class";
    private Paint mCirclePaint, mLinePaint, mSquarePaint;


    private int radius;                    //circle radius
    private int width, height;             //dimensions of circle
    private int cx, cy;                    //Center xy coordinates
    private float[] points = new float[6]; //Coordinates of the three points of the check mark
    private float correctProgress;
    private boolean isChecked;
    private boolean animating;

    private int animDuration = DEFAULT_ANIM_DURATION;
    private int uncheckColor = DEFAULT_UNCHECK_COLOR;
    private int circleColor = DEFAULT_CHECKED_COLOR;
    private int tickColor = DEFAULT_TICK_COLOR;
    private int tickWidth = DEFAULT_TICK_WIDTH;
    private int mStyle = DEFAULT_STYLE;
    private boolean hideTick = DEFAULT_HIDE_TICK;
    private OnCheckedChangeListener listener;
    private RectF square;
    private float maxH, top, bottom;


    public SwellCheckBox(Context context) {
        this(context, null);
    }

    public SwellCheckBox(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwellCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // Log.w(TAG,"contructor called");
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwellCheckBox, defStyleAttr, 0);

        circleColor = a.getColor(R.styleable.SwellCheckBox_checkedColor, DEFAULT_CHECKED_COLOR);
        uncheckColor = a.getColor(R.styleable.SwellCheckBox_unCheckColor, DEFAULT_UNCHECK_COLOR);
        tickColor = a.getColor(R.styleable.SwellCheckBox_lineColor, DEFAULT_TICK_COLOR);
        //tickWidth = a.getDimensionPixelSize(R.styleable.SwellCheckBox_lineWidth, DEFAULT_TICK_WIDTH);
        animDuration = a.getInteger(R.styleable.SwellCheckBox_animDuration, DEFAULT_ANIM_DURATION);
        //hideTick = a.getBoolean(R.styleable.SwellCheckBox_hideTick, DEFAULT_HIDE_TICK);
        mStyle = a.getInteger(R.styleable.SwellCheckBox_style, mStyle);

        init(context);
        a.recycle();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SwellCheckBox(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    private void init(Context context) {

        // Log.w(TAG,"init called");
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(circleColor);


        mSquarePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSquarePaint.setStyle(Paint.Style.FILL);
        mSquarePaint.setColor(circleColor);

        square = new RectF();
        //square.set(0,0,40,40);

        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    @Override
    public void setChecked(boolean checked) {
        if (isChecked && !checked) {
            hideCheck();
        } else if (!isChecked && checked) {
            showCheck();
        }
    }

    public void setUncheckStatus() {
        isChecked = false;
        radius = DEFAULT_RADIUS;
        correctProgress = 0;
        invalidate();
    }

    @Override
    public boolean isChecked() {
        return isChecked;
    }

    @Override
    public void toggle() {
        this.setChecked(!isChecked());
    }


    private void showUnChecked() {
        if (animating) {
            return;
        }

        animating = true;

        switch (mStyle) {
            case 0:
                ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(animDuration);
                va.setInterpolator(new LinearInterpolator());
                va.start();
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                        radius = (int) ((1 - value) * height * 0.375f + height * 0.125f);
                        if (value >= 1) {
                            isChecked = false;
                            animating = false;
                            if (listener != null) {
                                listener.onCheckedChanged(SwellCheckBox.this, false);
                            }
                        }
                        invalidate();
                    }
                });

                break;

            case 1:

                ValueAnimator vaa = ValueAnimator.ofFloat(0, 1).setDuration(animDuration*3);
                vaa.setInterpolator(new LinearInterpolator());
                vaa.start();
                vaa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                        radius = (int) ((1 - value) * height * 0.375f + height * 0.125f);
                       // float diff =  ((1 - value) * maxH * 0.375f + maxH * 0.05f);
                        square.top =  square.top>= top?  top: square.top +  (radius/2) ;
                        square.bottom = square.bottom<= bottom? bottom:square.bottom -  (radius/2) ;
                        //Log.w(TAG, "Square DIM:" + square.toString());
                        if (value >= 1) {
                            isChecked = false;
                            animating = false;
                            if (listener != null) {
                                listener.onCheckedChanged(SwellCheckBox.this, false);
                            }
                        }
                        invalidate();
                    }
                });

                break;
        }


    }

    private void showCheck() {
        if (animating) {
            return;
        }
        animating = true;
        switch (mStyle) {
            case 0:
                ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(animDuration);
                va.setInterpolator(new LinearInterpolator());
                va.start();
                va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                        radius = (int) (value * height * 0.37f + height * 0.125f);
                        if (value >= 1) {
                            isChecked = true;
                            animating = false;
                            if (listener != null) {
                                listener.onCheckedChanged(SwellCheckBox.this, true);
                            }

                        }
                        invalidate();
                    }
                });
                break;
            case 1:

                ValueAnimator vaa = ValueAnimator.ofFloat(0, 1).setDuration(animDuration*3);
                vaa.setInterpolator(new LinearInterpolator());
                vaa.start();
                vaa.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                        radius = (int) (value *  height * 0.37f + height * 0.125f);
                        //float diff = (int) (value *  height * 0.37f + height * 0.125f);
                        //Log.w(TAG, "diffVertical:" + radius + " height:" + height + " sq height:" + square.height());
                        square.top = square.top<=  (cy-maxH)? (cy-maxH) : square.top - (radius/2) ;
                        square.bottom = square.bottom >= (cy+maxH) ? (cy+maxH) : square.bottom + (radius/2);
                        //Log.w(TAG, "Square DIM:" + square.toString());
                        //square.top = (int) (value * height * 0.37f + height * 0.125f);
                        if (value >= 1) {
                            isChecked = true;
                            animating = false;
                            if (listener != null) {
                                listener.onCheckedChanged(SwellCheckBox.this, true);
                            }

                        }
                        invalidate();
                    }
                });
                break;
        }


    }

    /**
     * Show the tick animation
     */
    private void showTick() {
        if (animating) {
            return;
        }
        animating = true;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(animDuration);
        va.setInterpolator(new LinearInterpolator());
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                correctProgress = value;
                invalidate();
                if (value >= 1) {
                    animating = false;
                }
            }
        });
    }

    private void hideCheck() {
        if (animating) {
            return;
        }
        animating = true;
        ValueAnimator va = ValueAnimator.ofFloat(0, 1).setDuration(animDuration);
        va.setInterpolator(new LinearInterpolator());
        va.start();
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue(); // 0f ~ 1f
                correctProgress = 1 - value;
                invalidate();
                if (value >= 1) {
                    animating = false;
                    showUnChecked();
                }
            }
        });
    }


    /**
     * Determines the size coordinates
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //for the circle
        height = width = Math.min(w - getPaddingLeft() - getPaddingRight(), h - getPaddingBottom() - getPaddingTop());

        if (mStyle == 0 || mStyle == 1) {
            cx = w / 2;
            cy = h / 2;
            float d = (h * 0.050f);
            square.set(w * 0.325f, cy - d, w * 0.625f, cy + d);
            maxH = square.height()*2;
            bottom = square.bottom;
            top = square.top;

        }
        DEFAULT_RADIUS = radius = (int) (height * 0.125f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float f;
        switch (mStyle) {
            case 0:
                f = (radius - height * 0.125f) / (height * 0.5f); //Circle transition colour
                mCirclePaint.setColor(evaluate(f, uncheckColor, circleColor));
                canvas.drawCircle(cx, cy, radius, mCirclePaint);
                break;
            case 1:
                f = (radius - height * 0.125f) / (height * 0.3f);
                mSquarePaint.setColor(evaluate(f, uncheckColor, circleColor));
                //canvas.drawRect(square, mSquarePaint);
                canvas.drawRoundRect(square,DEFAULT_CORNER_RADIUS,DEFAULT_CORNER_RADIUS,mSquarePaint);
                break;

            default:
                break;
        }


    }

    /*
    * Fade transition between colors
    * **/
    private int evaluate(float fraction, int startValue, int endValue) {
        if (fraction <= 0) {
            return startValue;
        }
        if (fraction >= 1) {
            return endValue;
        }
        int startInt = startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return ((startA + (int) (fraction * (endA - startA))) << 24) | ((startR + (int) (fraction * (endR - startR))) << 16) | ((startG + (int) (fraction * (endG - startG))) << 8) | ((startB + (int) (fraction * (endB - startB))));
    }


    public interface OnCheckedChangeListener {
        void onCheckedChanged(View buttonView, boolean isChecked);
    }

    public boolean isAnimating() {
        return animating;
    }

    public void setAnimating(boolean animating) {
        this.animating = animating;
    }

    public int getAnimDuration() {
        return animDuration;
    }

    public void setAnimDuration(int animDuration) {
        this.animDuration = animDuration;
    }

    public int getUncheckColor() {
        return uncheckColor;
    }

    public void setUncheckColor(int uncheckColor) {
        this.uncheckColor = uncheckColor;
    }

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }


    public boolean isHideTick() {
        return hideTick;
    }

    public void setHideTick(boolean hideTick) {
        this.hideTick = hideTick;
    }

    public OnCheckedChangeListener getListener() {
        return listener;
    }

    public void setListener(OnCheckedChangeListener listener) {
        this.listener = listener;
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putBoolean(KEY_INSTANCE_STATE, isChecked());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            boolean isChecked = bundle.getBoolean(KEY_INSTANCE_STATE);
            setChecked(isChecked);
            super.onRestoreInstanceState(bundle.getParcelable(KEY_INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }
}
