package com.lq.library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/30/21 4:11 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/30/21 4:11 PM
 * @UpdateRemark:
 */
public class AkLogType {

    public static final int V = Log.VERBOSE;
    public static final int D = Log.DEBUG;
    public static final int I = Log.INFO;
    public static final int W = Log.WARN;
    public static final int E = Log.ERROR;
    public static final int A = Log.ASSERT;

    @IntDef({V, D, I, W, E, A})
    @Retention(RetentionPolicy.SOURCE)
    public @interface LogType {

    }
}
