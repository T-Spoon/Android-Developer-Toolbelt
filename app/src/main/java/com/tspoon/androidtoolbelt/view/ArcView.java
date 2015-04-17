package com.tspoon.androidtoolbelt.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import com.tspoon.androidtoolbelt.R;
import com.tspoon.androidtoolbelt.utils.Utils;

/**
 * Inspired by https://github.com/lzyzsd/CircleProgress
 */
public class ArcView extends View {

    private static final int ARC_ANGLE = (int) (360 * 0.8f);
    private final int COLOR_TEXT = Color.WHITE;
    private final int COLOR_FINISHED = Color.WHITE;
    private final int COLOR_UNFINISHED = Color.parseColor("#55FFFFFF");
    private final int MAX = 100;

    private RectF mRect = new RectF();
    private TextPaint mTextPaint = new TextPaint();
    private Paint mPaint = new Paint();

    private int mStrokeWidth;
    private int mTextSizeLarge;
    private int mTextSizeSmall;
    private float mArcBottomHeight;

    private float mProgress;
    private String mTextBottom;

    public ArcView(Context context) {
        super(context);
        init();
    }

    public ArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ArcView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        Resources res = getContext().getResources();

        mStrokeWidth = Utils.dpToPixels(res, 10);
        mTextSizeLarge = res.getDimensionPixelSize(R.dimen.text_size_arc_large);
        mTextSizeSmall = res.getDimensionPixelSize(R.dimen.text_size_arc_small);

        mTextPaint.setColor(COLOR_TEXT);
        mTextPaint.setTextSize(mTextSizeLarge);
        mTextPaint.setAntiAlias(true);

        mPaint = new Paint();
        mPaint.setColor(COLOR_UNFINISHED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setPathEffect(new DashPathEffect(new float[]{2, 2}, 0));
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        mProgress = progress;
        invalidate();
    }

    public void setTextBottom(String textBottom) {
        mTextBottom = textBottom;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        float radius = width / 2f;
        float angle = (360 - ARC_ANGLE) / 2f;

        mRect.set(mStrokeWidth / 2f, mStrokeWidth / 2f, width - mStrokeWidth / 2f, MeasureSpec.getSize(heightMeasureSpec) - mStrokeWidth / 2f);
        mArcBottomHeight = radius * (float) (1 - Math.cos(angle / 180 * Math.PI));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startAngle = 270 - ARC_ANGLE / 2f;
        float finishedSweepAngle = mProgress / (float) MAX * ARC_ANGLE;

        mPaint.setColor(COLOR_UNFINISHED);
        canvas.drawArc(mRect, startAngle, ARC_ANGLE, false, mPaint);
        mPaint.setColor(COLOR_FINISHED);
        canvas.drawArc(mRect, startAngle, finishedSweepAngle, false, mPaint);

        String text = String.valueOf((int) mProgress);
        if (!TextUtils.isEmpty(text)) {

            mTextPaint.setColor(COLOR_TEXT);
            mTextPaint.setTextSize(mTextSizeLarge);
            float textHeight = mTextPaint.descent() + mTextPaint.ascent();
            float textBaseline = (getHeight() - textHeight) / 2.0f;
            canvas.drawText(text, (getWidth() - mTextPaint.measureText(text)) / 2.0f, textBaseline, mTextPaint);

            mTextPaint.setColor(COLOR_UNFINISHED);
            mTextPaint.setTextSize(mTextSizeSmall);
            float suffixHeight = mTextPaint.descent() + mTextPaint.ascent();
            canvas.drawText("%", getWidth() / 2.0f + mTextPaint.measureText(text), textBaseline + textHeight - suffixHeight, mTextPaint);
        }

        if (!TextUtils.isEmpty(mTextBottom)) {
            mTextPaint.setColor(COLOR_TEXT);
            mTextPaint.setTextSize(mTextSizeSmall);
            float bottomTextBaseline = getHeight() - mArcBottomHeight - (mTextPaint.descent() + mTextPaint.ascent()) / 2;
            canvas.drawText(mTextBottom, (getWidth() - mTextPaint.measureText(mTextBottom)) / 2.0f, bottomTextBaseline, mTextPaint);
        }
    }
}
