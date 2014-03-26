package com.jpardogo.android.googleprogressbar.library;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class GoogleProgressBar extends ProgressBar {
    private static final int FOLDED_TRANSPARENCY = 235;
    private static final int OPAQUE=255;
    private int mY = Integer.MAX_VALUE;
    private int mX = Integer.MAX_VALUE;
    private Paint mPaint;
    private Paint mPaint2;
    private Paint mPaint3;
    private RectF mOval;
    private Path mPath;
    private int mHeight;
    private int mHalfHeight;
    private int mWidth;
    private int mHalfWidth;
    private ProgressStates mCurrentState = ProgressStates.FOLDING_DOWN;
    private ProgressStates mLastState;
    private int mSpeed;
    private int mDiameter;
    private int mUnfoldCounter = 0;

    private enum ProgressColors {
        RED("#C93437"),
        BLUE("#375BF1"),
        YELLOW("#F7D23E"),
        GREEN("#34A350");

        private String color;

        private ProgressColors(final String htmlCode) {
            this.color = htmlCode;
        }

        @Override
        public String toString() {
            return color;
        }
    }


    private enum ProgressStates {
        FOLDING_DOWN,
        FOLDING_UP,
        FOLDING_LEFT,
        FOLDING_RIGHT,
        NOT_FOLDING;
    }

    public GoogleProgressBar(Context context) {
        super(context);
        init(context);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mPath = new Path();

        mPaint = new Paint();
        mPaint.setColor(Color.parseColor(ProgressColors.RED.toString()));

        mPaint2 = new Paint();
        mPaint2.setColor(Color.parseColor(ProgressColors.BLUE.toString()));

        mPaint3 = new Paint();
        mPaint3.setColor(Color.parseColor(ProgressColors.BLUE.toString()));
        setIndeterminateDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
    }

    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mDiameter = Math.min(getWidth(), getHeight());
        mHalfWidth = mDiameter / 2;
        mHalfHeight = mDiameter / 2;
        mWidth=mDiameter;
        mHeight=mDiameter;
        mOval = new RectF(0, 0, mWidth, mHeight);
        mSpeed = mHeight/20;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mCurrentState == ProgressStates.FOLDING_DOWN) {
            //Start
            if (mY == Integer.MAX_VALUE || mY < -mHeight / 6) {
                mY = -mHeight / 6;
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //Half
            if (mY == mHeight / 2 || mY > mHeight / 2) {
                mPaint3.setColor(Color.parseColor(ProgressColors.RED.toString()));
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //End
            if (mY == mHeight + mHeight / 6 || mY > mHeight + mHeight / 6) {
                mY = mHeight + mHeight / 6;
                mCurrentState = ProgressStates.NOT_FOLDING;
                mLastState = ProgressStates.FOLDING_DOWN;
                mPaint2.setColor(mPaint3.getColor());
                mPaint2.setAlpha(OPAQUE);
            }

            mY = mY + mSpeed;
            canvas.drawArc(mOval, 0, -180, true, mPaint);
            canvas.drawArc(mOval, -180, -180, true, mPaint2);
            mPath.reset();
            mPath.moveTo(0, mHalfHeight);
            mPath.cubicTo(0, mY, mWidth, mY, mWidth, mHalfHeight);
            canvas.drawPath(mPath, mPaint3);
        } else if (mCurrentState == ProgressStates.FOLDING_LEFT) {

            //Start
            if (mX == Integer.MAX_VALUE || mX > mWidth + mWidth / 6) {
                mX = mWidth + mWidth / 6;
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //Half
            if (mX == mWidth / 2 || mX < mWidth / 2) {
                mPaint3.setColor(Color.parseColor(ProgressColors.YELLOW.toString()));
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //Final
            if (mX == 0 - mWidth / 6 || mX < 0 - mWidth / 6){
                mX = 0 - mWidth / 6;
                mCurrentState = ProgressStates.NOT_FOLDING;
                mLastState = ProgressStates.FOLDING_LEFT;
                mPaint.setColor(mPaint3.getColor());
                mPaint.setAlpha(OPAQUE);
            }

            mX = mX - mSpeed;
            canvas.drawArc(mOval, 90, 180, true, mPaint);
            canvas.drawArc(mOval, -270, -180, true, mPaint2);
            mPath.reset();
            mPath.moveTo(mHalfWidth, 0);
            mPath.cubicTo(mX, 0, mX, mHeight, mHalfWidth, mHeight);
            canvas.drawPath(mPath, mPaint3);
        } else if (mCurrentState == ProgressStates.FOLDING_UP) {

            //Start
            if (mY == mHeight + mHeight / 6 || mY > mHeight + mHeight / 6) {
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //Half
            if (mY == mHeight / 2 || mY < mHeight / 2) {
                mPaint3.setColor(Color.parseColor(ProgressColors.GREEN.toString()));
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //End
            if (mY == -mHeight / 6 || mY < -mHeight / 6) {
                mY = -mHeight / 6;
                mCurrentState = ProgressStates.NOT_FOLDING;
                mLastState = ProgressStates.FOLDING_UP;
                mPaint2.setColor(mPaint3.getColor());
                mPaint2.setAlpha(OPAQUE);
                mPaint.setColor(mPaint3.getColor());
                mPaint.setAlpha(OPAQUE);
            }

            mY = mY - mSpeed;
            canvas.drawArc(mOval, 0, -180, true, mPaint);
            canvas.drawArc(mOval, -180, -180, true, mPaint2);
            mPath.reset();
            mPath.moveTo(0, mHalfHeight);
            mPath.cubicTo(0, mY, mWidth, mY, mWidth, mHalfHeight);
            canvas.drawPath(mPath, mPaint3);
        } else if (mCurrentState == ProgressStates.FOLDING_RIGHT) {

            //Start
            if (mX == 0 - mWidth / 6 || mX < 0 - mWidth / 6) {
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }

            //Half
            if (mX == mWidth / 2 || mX > mWidth / 2) {
                mPaint3.setColor(Color.parseColor(ProgressColors.BLUE.toString()));
                mPaint3.setAlpha(FOLDED_TRANSPARENCY);
            }
            //End
            if (mX == mWidth + mWidth / 6 || mX > mWidth + mWidth / 6) {
                mX = mWidth + mWidth / 6;
                mCurrentState = ProgressStates.NOT_FOLDING;
                mLastState = ProgressStates.FOLDING_RIGHT;
                mPaint2.setColor(mPaint3.getColor());
                mPaint2.setAlpha(OPAQUE);
                mPaint.setColor(mPaint3.getColor());
                mPaint.setAlpha(OPAQUE);

            }

            mX = mX + mSpeed;
            canvas.drawArc(mOval, 90, 180, true, mPaint);
            canvas.drawArc(mOval, -270, -180, true, mPaint2);
            mPath.reset();
            mPath.moveTo(mHalfWidth, 0);
            mPath.cubicTo(mX, 0, mX, mHeight, mHalfWidth, mHeight);
            canvas.drawPath(mPath, mPaint3);

        } else if (mCurrentState == ProgressStates.NOT_FOLDING) {
            canvas.drawArc(mOval, 90, 180, true, mPaint);
            canvas.drawArc(mOval, -270, -180, true, mPaint2);

            if (mUnfoldCounter == 10) {
                mUnfoldCounter = 0;
                if (mLastState == ProgressStates.FOLDING_DOWN) {
                    mPaint2.setColor(Color.parseColor(ProgressColors.YELLOW.toString()));
                    mCurrentState = ProgressStates.FOLDING_LEFT;
                } else if (mLastState == ProgressStates.FOLDING_LEFT) {
                    mPaint2.setColor(Color.parseColor(ProgressColors.GREEN.toString()));
                    mCurrentState = ProgressStates.FOLDING_UP;
                } else if (mLastState == ProgressStates.FOLDING_UP) {
                    mPaint.setColor(Color.parseColor(ProgressColors.BLUE.toString()));
                    mCurrentState = ProgressStates.FOLDING_RIGHT;
                } else if (mLastState == ProgressStates.FOLDING_RIGHT) {
                    mPaint.setColor(Color.parseColor(ProgressColors.RED.toString()));
                    mCurrentState = ProgressStates.FOLDING_DOWN;
                }
            }
            mUnfoldCounter++;
        }
        super.onDraw(canvas);
    }
}
