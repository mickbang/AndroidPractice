package com.lq.view.widgets.event;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/30/21 3:33 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 6/30/21 3:33 PM
 * @UpdateRemark:
 */
public class EventChildView extends View {
    private static final String TAG = "EventChildView";

    public EventChildView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            Log.d(TAG, "view->dispatchTouchEvent: ");
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN)
            Log.d(TAG, "view->onTouchEvent: ");
        return super.onTouchEvent(event);
    }
}
