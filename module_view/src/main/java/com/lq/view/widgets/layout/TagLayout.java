package com.lq.view.widgets.layout;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/18/21 11:49 AM
 *
 * @UpdateUser: mick
 * @UpdateDate: 6/18/21 11:49 AM
 * @UpdateRemark:
 */
public class TagLayout extends ViewGroup {
    private static final String TAG = "TagLayout";
    final List<Rect> childBounds = new ArrayList<>();

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int heightUsed = 0;

        int lineMaxHeight = 0;
        int lineWidthUsed = 0;

        int withSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int withSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);

            //处理换行逻辑
            if (withSpecMode != MeasureSpec.UNSPECIFIED && lineWidthUsed + child.getMeasuredWidth() > withSpecSize) {
                lineWidthUsed = 0;
                heightUsed += lineMaxHeight;
                lineMaxHeight = 0;

                //heightUsed改变了这里需要重新测量一次
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }

            Rect bound;
            if (i < childBounds.size()) {
                bound = childBounds.get(i);
            } else {
                bound = new Rect();
                childBounds.add(bound);
            }
            bound.set(lineWidthUsed, heightUsed,
                    lineWidthUsed + child.getMeasuredWidth(),
                    heightUsed + child.getMeasuredHeight());

            lineWidthUsed += child.getMeasuredWidth();
            widthUsed = Math.max(lineWidthUsed, widthUsed);
            lineMaxHeight = Math.max(lineMaxHeight, child.getMeasuredHeight());
        }

        int width = widthUsed;
        int height = heightUsed+lineMaxHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (i < childBounds.size()) {
                Rect bound = childBounds.get(i);
                child.layout(bound.left, bound.top, bound.right, bound.bottom);
            }
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}
