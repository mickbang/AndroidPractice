package com.lq.library.log;

import com.lq.library.log.format.AkLogThreadFormat;
import com.lq.library.log.format.AkStackTraceFormat;
import com.lq.library.log.printer.IAkLogPrinter;

import java.util.List;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/30/21 4:12 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/30/21 4:12 PM
 * @UpdateRemark:
 */
public abstract class AkLogConfig {
    public static final int LINE_MAX_LEN = 128;
    public final static AkLogThreadFormat AK_LOG_THREAD_FORMAT = new AkLogThreadFormat();
    public final static AkStackTraceFormat AK_STACK_TRACE_FORMAT = new AkStackTraceFormat();

    public String getGlobalTag() {
        return "AkLog";
    }

    public boolean enable() {
        return true;
    }

    public boolean threadEnable() {
        return false;
    }

    public int stackTraceDepth() {
        return 0;
    }

    public JsonParser getJsonParser() {
        return null;
    }

    public List<IAkLogPrinter> getLogPrinter() {
        return null;
    }
}
