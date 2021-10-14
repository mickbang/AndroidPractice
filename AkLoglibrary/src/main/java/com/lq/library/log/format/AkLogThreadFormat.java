package com.lq.library.log.format;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/31/21 1:55 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/31/21 1:55 PM
 * @UpdateRemark:
 */
public class AkLogThreadFormat implements IAkLogFormat<Thread> {
    @Override
    public String format(Thread thread) {
        return "thread:"+thread.getName();
    }
}
