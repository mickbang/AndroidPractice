package com.lq.library.log.utils;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/31/21 2:14 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/31/21 2:14 PM
 * @UpdateRemark:
 */
public class StackTraceElementUtils {


    /**
     * 删除某个包名下的stack信息,并保留最大深度为maxDepth的打印信息
     *
     * @param stackTraceElements
     * @param exceptPackage
     * @param maxDepth
     * @return
     */
    public static StackTraceElement[] realStackTraceElements(StackTraceElement[] stackTraceElements,
                                                             String exceptPackage,
                                                             int maxDepth) {

        return cropStackTraceElementsByLen(
                cropStackTraceElementsByPackage(stackTraceElements, exceptPackage),
                maxDepth
        );
    }

    /**
     * 去除某个包名下的stack trace信息
     *
     * @param stackTraceElements
     * @param target
     * @return
     */
    private static StackTraceElement[] cropStackTraceElementsByPackage(
            StackTraceElement[] stackTraceElements,
            String target
    ) {
        if (stackTraceElements == null) {
            return null;
        }
        if (stackTraceElements.length == 0) {
            return null;
        }

        int resultLen = 0;
        for (int i = stackTraceElements.length - 1; i >= 0; i--) {
            StackTraceElement stackTraceElement = stackTraceElements[i];
            if (stackTraceElement != null && stackTraceElement.getClassName().startsWith(target)) {
                break;
            }
            resultLen++;
        }
        StackTraceElement[] retain = new StackTraceElement[resultLen];
        System.arraycopy(stackTraceElements, stackTraceElements.length-resultLen, retain, 0, resultLen);
        return retain;
    }

    /**
     * 保留指定长度的stack trace
     *
     * @param stackTraceElements
     * @param maxLen
     * @return
     */
    private static StackTraceElement[] cropStackTraceElementsByLen(
            StackTraceElement[] stackTraceElements,
            int maxLen
    ) {
        if (stackTraceElements == null) {
            return null;
        }
        int resultLen = Math.min(stackTraceElements.length, maxLen);
        StackTraceElement[] result = new StackTraceElement[resultLen];
        System.arraycopy(stackTraceElements, 0, result, 0, resultLen);
        return result;
    }
}
