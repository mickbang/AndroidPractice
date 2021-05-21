package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 5/21/21 2:06 PM
 * @UpdateUser: mick
 * @UpdateDate: 5/21/21 2:06 PM
 * @UpdateRemark:
 */
public class MeterView extends View {
    public static final float ANGLE = 120f;
    public static final float LENGTH = Utils.dp2Px(100f);
    public static final float RADIUS = Utils.dp2Px(150f);

    RectF rectF;
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    PathDashPathEffect pathDashPathEffect;

    RectF dashRect;

    {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2Px(2));

        dashRect = new RectF(0, 0, Utils.dp2Px(2), Utils.dp2Px(5));

        Path path = new Path();
        path.addArc((getWidth() / 2) - RADIUS, getHeight() / 2 - RADIUS,
                (getWidth() / 2) + RADIUS,
                getHeight() / 2 + RADIUS, 90 + ANGLE / 2, 360f - ANGLE);

        Path dash = new Path();
        dash.addRect(dashRect, Path.Direction.CW);

        PathMeasure pathMeasure = new PathMeasure(path, false);

        pathDashPathEffect = new PathDashPathEffect(dash, (pathMeasure.getLength() - Utils.dp2Px(2)) / 25f, 0f, PathDashPathEffect.Style.MORPH);
    }

    public MeterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF((getWidth() / 2) - RADIUS, getHeight() / 2 - RADIUS,
                (getWidth() / 2) + RADIUS,
                getHeight() / 2 + RADIUS);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画圆
        canvas.drawArc(rectF, 90 + ANGLE / 2, 360f - ANGLE, false, paint);

        //刻度
        paint.setPathEffect(pathDashPathEffect);
        canvas.drawArc(rectF, 90 + ANGLE / 2, 360f - ANGLE, false, paint);
        paint.setPathEffect(null);

        //指针
        canvas.drawLine(getWidth() / 2,
                getHeight() / 2,
                (float) (Math.cos(Math.toRadians(getAngleFromMarker(5))) * LENGTH) + getWidth() / 2,
                (float) (Math.sin(Math.toRadians(getAngleFromMarker(5))) * LENGTH) + getHeight() / 2,
                paint);
    }


    private float getAngleFromMarker(int marker) {
        return 90f + ANGLE / 2f + (360f - ANGLE) / 25f * marker;
    }
}
