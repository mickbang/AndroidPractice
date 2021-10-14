package com.lq.library.log.printer;

import com.lq.library.log.AkLogType;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/31/21 1:58 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/31/21 1:58 PM
 * @UpdateRemark:
 */
public interface IAkLogPrinter {
    void print(@AkLogType.LogType int type,
               String tag,
               String msg);
}
