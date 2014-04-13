package com.jpardogo.android.googleprogressbar.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

public class GoogleProgressBar extends ProgressBar {

    private enum ProgressType{
        FOLDING_CIRCLES
    }

    public GoogleProgressBar(Context context) {
        this(context, null);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs,android.R.attr.progressBarStyle);
    }

    public GoogleProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.GoogleProgressBar, defStyle, 0);
        final int typeIndex = a.getInteger(R.styleable.GoogleProgressBar_type, 0);
        a.recycle();

        Drawable drawable = buildDrawable(context,typeIndex);
        if(drawable!=null)
        setIndeterminateDrawable(drawable);
    }

    private Drawable buildDrawable(Context context, int typeIndex) {
        Drawable drawable = null;
        ProgressType type = ProgressType.values()[typeIndex];
        switch (type){
            case FOLDING_CIRCLES:
                FoldingCirclesDrawable.Builder builder = new FoldingCirclesDrawable.Builder(context);
                drawable = builder.build();
                break;
        }

        return drawable;
    }
}
