package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 6/4/21 4:08 PM
 * @UpdateUser: mick
 * @UpdateDate: 6/4/21 4:08 PM
 * @UpdateRemark:
 */
public class CustomText extends View {
    public static final float RADIUS = Utils.dp2Px(100);
    public static final float GAP = Utils.dp2Px(10);
    Paint paint;
    String text = "My English is very good!";

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public CustomText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2Px(2));
        canvas.drawCircle(centerX, centerY, RADIUS, paint);

        paint.setColor(Color.GREEN);
        canvas.drawCircle(centerX, centerY, RADIUS + GAP, paint);

        paint.setColor(Color.BLUE);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(GAP);
        canvas.drawArc(centerX - RADIUS - GAP / 2, centerY - RADIUS - GAP / 2,
                centerX + RADIUS + GAP / 2, centerY + RADIUS + GAP / 2,
                0, -120, false, paint);

        paint.setTextSize(Utils.dp2Px(18));
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        //使用getTextBound获取尺寸
//        Rect textRect = new Rect();
//        paint.getTextBounds(text, 0, text.length(), textRect);
//        //textRect.top为负值,所以这里使用-
//        canvas.drawText(text, centerX, centerY-(textRect.top+textRect.bottom)/2f, paint);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        //ascent也为负
        canvas.drawText(text, centerX, centerY-(fontMetrics.ascent+fontMetrics.descent)/2f, paint);

    }
}
