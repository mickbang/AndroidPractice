package com.lq.view.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lq.view.R;
import com.lq.view.utils.Utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 5/21/21 4:11 PM
 * @UpdateUser: mick
 * @UpdateDate: 5/21/21 4:11 PM
 * @UpdateRemark:
 */
public class HeadView extends View {
    public static final float RADIUS = Utils.dp2Px(300f);
    private static final String TAG = "HeadView";
    private static final float PADDING = Utils.dp2Px(50);

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;

    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    RectF saveArea = new RectF();

    {
        paint.setColor(Color.BLUE);
        bitmap = getAvatar((int) RADIUS);
    }

    public HeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        saveArea.set(PADDING,
                PADDING,
                RADIUS + PADDING,
                RADIUS + PADDING);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawOval(saveArea, paint);
        int saved = canvas.saveLayer(saveArea, paint);
        paint.setXfermode(xfermode);
        canvas.drawBitmap(bitmap, PADDING, PADDING, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(saved);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.head, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.head, options);
    }
}


