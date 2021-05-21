package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.view.utils.Utils;


/**
 * Implementation of App Widget functionality.
 */
public class PieView extends View {
    public static final float RADIUS = Utils.dp2Px(150);
    public static final float LENGTH = Utils.dp2Px(5);
    public static final int PULL_OUT_INDEX = 1;
    private static final String TAG = "PieView";
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    float[] angles = {60, 100, 120, 80};
    int[] colors = {Color.parseColor("#2979FF"), Color.parseColor("#C2185B"),
            Color.parseColor("#009688"), Color.parseColor("#FF8F00")};

    RectF bounds = new RectF();

    public PieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bounds.set(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS, getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float currentAngle = 0;
        for (int i = 0; i < angles.length; i++) {
            paint.setColor(colors[i]);
            canvas.save();
            if (i == PULL_OUT_INDEX) {
                canvas.translate((float) Math.cos(Math.toRadians(currentAngle + angles[i]/2)) * LENGTH,
                        (float) Math.sin(Math.toRadians(currentAngle + angles[i]/2)) * LENGTH);
            }
            canvas.drawArc(bounds, currentAngle, angles[i], true, paint);
            canvas.restore();
            currentAngle += angles[i];
        }

    }
}