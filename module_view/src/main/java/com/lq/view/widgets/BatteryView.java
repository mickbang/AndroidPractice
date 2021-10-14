package com.lq.view.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/2/21 10:37 AM
 *
 * @UpdateUser: mick
 * @UpdateDate: 8/2/21 10:37 AM
 * @UpdateRemark:
 */
public class BatteryView extends View {
    public static final int OVAL_RADIUS_LARGER = 15;
    public static final int OVAL_RADIUS = 10;
    public static final int STOKE_WITH = 2;
    public static final int BODY_GAP = 8;
    public static final int BORDER_RADIUS = 20;//电池轮廓的圆角
    public static final int BORDER_RADIUS_INNER = 15;//电池内部电量的圆角
    public static final int THEM_COLOR = Color.parseColor("#16B2BA");//电池内部电量的圆角

    private float quantity = 0.05f;//电量0-1
    private Paint paint;

    {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(THEM_COLOR);
    }

    public BatteryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制电池帽子
        paint.setColor(THEM_COLOR);
        paint.setStyle(Paint.Style.FILL);
        RectF ovalRect = new RectF();
        ovalRect.left = getWidth() / 2 - Utils.dp2Px(OVAL_RADIUS_LARGER);
        ovalRect.top = 0;
        ovalRect.right = getWidth() / 2 + Utils.dp2Px(OVAL_RADIUS_LARGER);
        ovalRect.bottom = Utils.dp2Px(OVAL_RADIUS) * 2;
        canvas.drawArc(ovalRect, 180, 180, true, paint);

        //绘制边框
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(Utils.dp2Px(STOKE_WITH));
        RectF bodyRect = new RectF();
        bodyRect.left = Utils.dp2Px(STOKE_WITH) / 2;
        bodyRect.top = Utils.dp2Px(OVAL_RADIUS) + Utils.dp2Px(5);
        bodyRect.right = getWidth() - dp2Px(STOKE_WITH) / 2;
        bodyRect.bottom = getHeight() - dp2Px(STOKE_WITH) / 2;
        canvas.drawRoundRect(bodyRect, dp2Px(BORDER_RADIUS), dp2Px(BORDER_RADIUS), paint);

        if (quantity > 0) {
            //绘制电池电量
            paint.setStyle(Paint.Style.FILL);
            float top = dp2Px(OVAL_RADIUS) + dp2Px(5) + dp2Px(BODY_GAP) + dp2Px(STOKE_WITH);
            float bottom = bodyRect.bottom - dp2Px(BODY_GAP);

            float addHeight = (1 - quantity) * (bottom - top);
            //中间部分
            RectF inner = new RectF();
            inner.left = bodyRect.left + dp2Px(BODY_GAP);
            inner.top = top + addHeight;
            inner.right = bodyRect.right - dp2Px(BODY_GAP);
            inner.bottom = bottom;
            canvas.drawRoundRect(inner, dp2Px(BORDER_RADIUS_INNER), dp2Px(BORDER_RADIUS_INNER), paint);

            if (bottom - inner.top > dp2Px(BORDER_RADIUS_INNER) && quantity < 1) {
                paint.setColor(THEM_COLOR);
                RectF cover = new RectF(inner);
                cover.bottom = cover.top + dp2Px(BORDER_RADIUS_INNER);
                canvas.drawRect(cover, paint);
            }
        }

    }

    public static float dp2Px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                Resources.getSystem().getDisplayMetrics());
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
        invalidate();
    }
}
