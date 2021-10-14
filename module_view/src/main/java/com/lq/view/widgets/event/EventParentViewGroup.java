package com.lq.view.widgets.event;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/30/21 3:31 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 6/30/21 3:31 PM
 * @UpdateRemark:
 */
public class EventParentViewGroup extends FrameLayout {
    private static final String TAG = "EventParentViewGroup";

    public EventParentViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            Log.d(TAG, "viewGroup->dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            Log.d(TAG, "viewGroup->onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(event);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            Log.d(TAG, "viewGroup->onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
