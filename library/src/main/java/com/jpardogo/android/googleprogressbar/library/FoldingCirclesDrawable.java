package com.jpardogo.android.googleprogressbar.library;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * FoldingCirclesDrawable
 * User: romainpiel
 * Date: 07/04/2014
 * Time: 13:00
 */
public class FoldingCirclesDrawable extends Drawable implements Drawable.Callback {

    private static final float MAX_LEVEL = 10000;
    private static final float CIRCLE_COUNT = ProgressStates.values().length;
    private static final float MAX_LEVEL_PER_CIRCLE = MAX_LEVEL / CIRCLE_COUNT;
    private static final int ALPHA_OPAQUE = 255;
    private static final int ALPHA_OVERLAY_DEFAULT = 235;

    private Paint mFstHalfPaint;
    private Paint mScndHalfPaint;
    private Paint mAbovePaint;
    private RectF mOval = new RectF();
    private int mDiameter;
    private Path mPath;
    private int mHalf;
    private ProgressStates mCurrentState;
    private int mControlPointMinimum;
    private int mControlPointMaximum;
    private int mAxisValue;
    private int mAlpha = ALPHA_OPAQUE;
    private ColorFilter mColorFilter;

    private enum ProgressColors {

        RED(0xFFC93437),
        BLUE(0xFF375BF1),
        YELLOW(0xFFF7D23E),
        GREEN(0xFF34A350);

        private int color;

        private ProgressColors(final int color) {
            this.color = color;
        }
    }


    private enum ProgressStates {

        FOLDING_DOWN(ProgressColors.RED, ProgressColors.BLUE, false),
        FOLDING_LEFT(ProgressColors.RED, ProgressColors.YELLOW, true),
        FOLDING_UP(ProgressColors.YELLOW, ProgressColors.GREEN, true),
        FOLDING_RIGHT(ProgressColors.BLUE, ProgressColors.GREEN, false);

        private ProgressColors fstColor, scndColor;
        private boolean goesBackward;

        ProgressStates(ProgressColors fstColor, ProgressColors scndColor, boolean goesBackward) {
            this.fstColor = fstColor;
            this.scndColor = scndColor;
            this.goesBackward = goesBackward;
        }
    }

    public FoldingCirclesDrawable() {
        initCirclesProgress();
    }

    private void initCirclesProgress() {
        mPath = new Path();

        Paint basePaint = new Paint();
        basePaint.setAntiAlias(true);

        mFstHalfPaint = new Paint(basePaint);
        mScndHalfPaint = new Paint(basePaint);
        mAbovePaint = new Paint(basePaint);

        // init alpha and color filter
        setAlpha(mAlpha);
        setColorFilter(mColorFilter);
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
        measureCircleProgress(bounds.width(), bounds.height());
    }

    @Override
    protected boolean onLevelChange(int level) {

        // level goes from 0 to 10000 but the number of colors divides 10000
        // so we need to do that hack that maps level 10000 to level 0
        int animationLevel = level == MAX_LEVEL ? 0 : level;

        // state
        int stateForLevel = (int) (animationLevel / MAX_LEVEL_PER_CIRCLE);
        mCurrentState = ProgressStates.values()[stateForLevel];

        // colors
        int levelForCircle = (int) (animationLevel % MAX_LEVEL_PER_CIRCLE);

        boolean halfPassed;
        if (!mCurrentState.goesBackward) {
            halfPassed = levelForCircle != (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
        } else {
            halfPassed = levelForCircle == (int) (animationLevel % (MAX_LEVEL_PER_CIRCLE / 2));
            levelForCircle = (int) (MAX_LEVEL_PER_CIRCLE - levelForCircle);
        }

        mFstHalfPaint.setColor(mCurrentState.fstColor.color);
        mScndHalfPaint.setColor(mCurrentState.scndColor.color);

        if (!halfPassed) {
            mAbovePaint.setColor(mScndHalfPaint.getColor());
        } else {
            mAbovePaint.setColor(mFstHalfPaint.getColor());
        }

        // invalidate alpha (Paint#setAlpha is a shortcut for setColor(alpha part)
        // so alpha is affected by setColor())
        setAlpha(mAlpha);

        // axis
        mAxisValue = (int) (mControlPointMinimum + (mControlPointMaximum - mControlPointMinimum) * (levelForCircle / MAX_LEVEL_PER_CIRCLE));

        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        if (mCurrentState != null) {
            makeCirclesProgress(canvas);
        }
    }

    private void measureCircleProgress(int width, int height) {
        mDiameter = Math.min(width, height);
        mHalf = mDiameter / 2;
        mOval.set(0, 0, mDiameter, mDiameter);
        mControlPointMinimum = -mDiameter / 6;
        mControlPointMaximum = mDiameter + mDiameter / 6;
    }

    private void makeCirclesProgress(Canvas canvas) {

        switch (mCurrentState) {
            case FOLDING_DOWN:
            case FOLDING_UP:
                drawYMotion(canvas);
                break;
            case FOLDING_RIGHT:
            case FOLDING_LEFT:
                drawXMotion(canvas);
                break;
        }

        canvas.drawPath(mPath, mAbovePaint);
    }

    private void drawXMotion(Canvas canvas) {
        canvas.drawArc(mOval, 90, 180, true, mFstHalfPaint);
        canvas.drawArc(mOval, -270, -180, true, mScndHalfPaint);
        mPath.reset();
        mPath.moveTo(mHalf, 0);
        mPath.cubicTo(mAxisValue, 0, mAxisValue, mDiameter, mHalf, mDiameter);
    }

    private void drawYMotion(Canvas canvas) {
        canvas.drawArc(mOval, 0, -180, true, mFstHalfPaint);
        canvas.drawArc(mOval, -180, -180, true, mScndHalfPaint);
        mPath.reset();
        mPath.moveTo(0, mHalf);
        mPath.cubicTo(0, mAxisValue, mDiameter, mAxisValue, mDiameter, mHalf);
    }

    @Override
    public void setAlpha(int alpha) {
        this.mAlpha = alpha;

        mFstHalfPaint.setAlpha(alpha);
        mScndHalfPaint.setAlpha(alpha);
        mAbovePaint.setAlpha(Math.min(mAlpha, ALPHA_OVERLAY_DEFAULT));
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        this.mColorFilter = cf;

        mFstHalfPaint.setColorFilter(cf);
        mScndHalfPaint.setColorFilter(cf);
        mAbovePaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void invalidateDrawable(Drawable who) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.invalidateDrawable(this);
        }
    }

    @Override
    public void scheduleDrawable(Drawable who, Runnable what, long when) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.scheduleDrawable(this, what, when);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable who, Runnable what) {
        final Callback callback = getCallback();
        if (callback != null) {
            callback.unscheduleDrawable(this, what);
        }
    }
}
