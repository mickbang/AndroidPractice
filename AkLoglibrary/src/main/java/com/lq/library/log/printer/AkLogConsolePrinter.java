package com.lq.library.log.printer;

import android.util.Log;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/31/21 2:01 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/31/21 2:01 PM
 * @UpdateRemark:
 */
public class AkLogConsolePrinter implements IAkLogPrinter {
    public static final int LINE_MAX_LEN = 128;

    @Override
    public void print(int type, String tag, String msg) {
//        int realLen = msg.length();
//        if (realLen > LINE_MAX_LEN) {
//            int piece = realLen / LINE_MAX_LEN;
//            StringBuilder sb = new StringBuilder();
//            int start = 0;
//            for (int i = 0; i < piece; i++) {
//                sb.append(msg.substring(start, start + LINE_MAX_LEN) + "\n");
//                start += LINE_MAX_LEN;
//            }
//            sb.append(msg.substring(start, realLen));
//            Log.println(type, tag, sb.toString());
//        } else {
            Log.println(type, tag, msg);
//        }
    }
}
