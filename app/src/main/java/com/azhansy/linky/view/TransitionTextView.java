package com.azhansy.linky.view;

import android.animation.ArgbEvaluator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.TextView;

import com.azhansy.linky.R;

/**
 * Created by SHU on 2016/7/4.
 */
public class TransitionTextView extends TextView implements PagerSlidingIndicator.IColorTransition {

    private int mStartColor = Color.BLACK;
    private int mEndColor = Color.BLACK;
    ArgbEvaluator mArgbEvaluator = new ArgbEvaluator();

    public TransitionTextView(Context context) {
        this(context, null);
    }

    public TransitionTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransitionTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TransitionTextView, defStyleAttr, 0);
        mStartColor = array.getColor(R.styleable.TransitionTextView_transition_start_color, mStartColor);
        mEndColor = array.getColor(R.styleable.TransitionTextView_transition_end_color, mEndColor);
        array.recycle();
    }

    @Override
    public void transitionColor(float fraction) {
        setTextColor((Integer) mArgbEvaluator.evaluate(fraction, mStartColor, mEndColor));
    }

    public void setStartColor(int startColor) {
        mStartColor = startColor;
        transitionColor(0);
    }

    public void setEndColor(int endColor) {
        mEndColor = endColor;
        transitionColor(0);
    }
}
