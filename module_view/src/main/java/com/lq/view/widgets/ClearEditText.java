package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GestureDetectorCompat;

import com.lq.view.R;
import com.lq.view.utils.Utils;

/**
 * @Description:
 * 功能:
 * 1.设置clearText按钮,可自定义图标
 * 2.设置错误提示
 *
 * @author: mick
 * @CreateAt: 6/23/21 5:04 PM
 *
 * @UpdateUser: mick
 * @UpdateDate: 6/23/21 5:04 PM
 * @UpdateRemark:
 */
public class ClearEditText extends androidx.appcompat.widget.AppCompatEditText {
    public static final float CLEAR_HOR_OFFSET = Utils.dp2Px(5);
    public static final float CLEAR_VER_OFFSET = Utils.dp2Px(10);
    public static final float CLEAR_RIGHT_OFFSET = Utils.dp2Px(5);
    private static final String TAG = "ClearEditText";
    int clearIconWidth = (int) Utils.dp2Px(18);
    Bitmap clearBitmap;
    Paint clearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    RectF clearRectF = new RectF();
    boolean isDownClear = false;

    public ClearEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        clearBitmap = Utils.getAvatar(getResources(), clearIconWidth, R.drawable.delete);

        setPadding(getPaddingLeft(), getPaddingTop(), (int) (getPaddingRight() + clearIconWidth + CLEAR_HOR_OFFSET), getPaddingBottom());
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        String s = getText().toString();
        if (hasFocus()&&!TextUtils.isEmpty(s)) {
            clearRectF.set(
                    getWidth() - clearIconWidth - CLEAR_RIGHT_OFFSET,
                    CLEAR_VER_OFFSET,
                    getWidth() + clearIconWidth,
                    CLEAR_VER_OFFSET + clearIconWidth);
            canvas.drawBitmap(clearBitmap, getWidth() - clearIconWidth - CLEAR_RIGHT_OFFSET, CLEAR_VER_OFFSET, clearPaint);
        }


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (hasFocus() && x >= clearRectF.left && x <= clearRectF.right) {
                    isDownClear = true;
                } else {
                    isDownClear = false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                if (hasFocus() && x >= clearRectF.left && x <= clearRectF.right && isDownClear) {
                    Log.d(TAG, "onTouchEvent: 点击了clear");
                    onClearClick();
                    isDownClear = false;
                    return true;
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    void onClearClick() {
        setText("");
    }
}
