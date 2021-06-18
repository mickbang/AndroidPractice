package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewDebug;

import androidx.annotation.Nullable;

import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/7/21 4:42 PM
 * @UpdateUser: mick
 * @UpdateDate: 6/7/21 4:42 PM
 * @UpdateRemark:
 */
public class MutliLineText extends View {
    public static final float TEXT_SIZE = Utils.dp2Px(15);
    public static final String TEXT = "My English is very good! My English is very good!My English is very good!My English is very good!My English is very good!My English is very good!";
    TextPaint paint;
    float[] withMeature = new float[]{1};

    {
        paint = new TextPaint();
        paint.setStyle(Paint.Style.FILL);
        paint.setTextSize(TEXT_SIZE);
        paint.setColor(Color.BLUE);
    }

    public MutliLineText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //使用StaticLayout换行
//        StaticLayout staticLayout = new StaticLayout(TEXT,paint,getWidth(), Layout.Alignment.ALIGN_NORMAL,1.0f,0f,false);
//        staticLayout.draw(canvas);

        //使用breakText换行
        int length = TEXT.length();
        float topOffset = -paint.getFontMetrics().top;
        int count = 0;
        for (int start = 0; start < length; ) {
            count = paint.breakText(TEXT, start, length, true, getWidth(), withMeature);
            canvas.drawText(TEXT, start, start + count, 0, topOffset, paint);
            start += count;
            topOffset += paint.getFontSpacing();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
