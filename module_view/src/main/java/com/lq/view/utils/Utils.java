package com.lq.view.utils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.TypedValue;

import androidx.annotation.DrawableRes;

import com.lq.view.R;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 5/21/21 2:02 PM
 * @UpdateUser: mick
 * @UpdateDate: 5/21/21 2:02 PM
 * @UpdateRemark:
 */
public class Utils {
    public static float dp2Px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpValue,
                Resources.getSystem().getDisplayMetrics());
    }

    public static Bitmap getAvatar(Resources res, int width,@DrawableRes int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
