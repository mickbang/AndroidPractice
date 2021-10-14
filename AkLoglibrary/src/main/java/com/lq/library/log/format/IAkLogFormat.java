package com.lq.library.log.format;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/31/21 1:53 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/31/21 1:53 PM
 * @UpdateRemark:
 */
public interface IAkLogFormat<T> {
    String format(T t);
}
