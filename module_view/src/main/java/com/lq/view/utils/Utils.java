package com.lq.view.utils;

import android.content.res.Resources;
import android.util.TypedValue;

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
}
