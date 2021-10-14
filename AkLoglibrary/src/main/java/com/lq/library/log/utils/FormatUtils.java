package com.lq.library.log.utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 9/2/21 9:38 AM
 * @UpdateUser: mick
 * @UpdateDate: 9/2/21 9:38 AM
 * @UpdateRemark:
 */
public class FormatUtils {
    public static String formatBody(String body, int lineMaxLen) {
        int realLen = body.length();
        if (realLen > lineMaxLen) {
            int piece = realLen / lineMaxLen;
            StringBuilder sb = new StringBuilder();
            int start = 0;
            for (int i = 0; i < piece; i++) {
                sb.append(body.substring(start, start + lineMaxLen) + "\n");
                start += lineMaxLen;
            }
            sb.append(body.substring(start, realLen));
            return sb.toString();
        } else {
            return body;
        }
    }
}
