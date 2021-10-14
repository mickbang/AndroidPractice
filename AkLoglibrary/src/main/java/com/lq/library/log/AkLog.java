package com.lq.library.log;

import androidx.annotation.NonNull;

import com.lq.library.log.printer.IAkLogPrinter;
import com.lq.library.log.utils.FormatUtils;
import com.lq.library.log.utils.StackTraceElementUtils;

import java.util.List;

import static com.lq.library.log.AkLogConfig.AK_LOG_THREAD_FORMAT;
import static com.lq.library.log.AkLogConfig.AK_STACK_TRACE_FORMAT;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/30/21 4:10 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/30/21 4:10 PM
 * @UpdateRemark:
 */
public class AkLog {
    public static final String EXCEPT_PACKAGE = "com.lq.library.log";

    public static void v(Object... objects) {
        log(AkLogType.V, objects);
    }

    public static void vt(@NonNull String tag, Object... objects) {
        log(AkLogType.V, tag, objects);
    }

    public static void d(Object... objects) {
        log(AkLogType.D, objects);
    }

    public static void dt(@NonNull String tag, Object... objects) {
        log(AkLogType.D, tag, objects);
    }

    public static void i(Object... objects) {
        log(AkLogType.I, objects);
    }

    public static void it(@NonNull String tag, Object... objects) {
        log(AkLogType.I, tag, objects);
    }

    public static void w(Object... objects) {
        log(AkLogType.W, objects);
    }

    public static void wt(@NonNull String tag, Object... objects) {
        log(AkLogType.W, tag, objects);
    }

    public static void e(Object... objects) {
        log(AkLogType.E, objects);
    }

    public static void et(@NonNull String tag, Object... objects) {
        log(AkLogType.E, tag, objects);
    }

    public static void a(Object... objects) {
        log(AkLogType.V, objects);
    }

    public static void at(@NonNull String tag, Object... objects) {
        log(AkLogType.V, tag, objects);
    }


    public static void log(@AkLogType.LogType int type, Object... objects) {
        log(AkLogManager.getInstance().getLogConfig(),
                type,
                AkLogManager.getInstance().getLogConfig().getGlobalTag(),
                objects
        );
    }

    public static void log(@AkLogType.LogType int type, String tag, Object... objects) {
        log(AkLogManager.getInstance().getLogConfig(),
                type,
                tag,
                objects
        );
    }


    public static void log(@NonNull AkLogConfig config,
                           @AkLogType.LogType int type,
                           String tag,
                           Object... objects) {
        if (!config.enable()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("========This is the custom log info======" + "\n");
        //添加当前线程信息
        if (config.threadEnable()) {
            sb.append(AK_LOG_THREAD_FORMAT.format(Thread.currentThread()) + "\n");
        }

        //添加stack trace信息
        if (config.stackTraceDepth() > 0) {
            sb.append(AK_STACK_TRACE_FORMAT.format(
                    StackTraceElementUtils.realStackTraceElements(
                            new Throwable().getStackTrace(),
                            EXCEPT_PACKAGE,
                            config.stackTraceDepth())
                    )
                            + "\n"
            );
        }
        String body = parseBody(config, objects);
        sb.append(body);
        String result = sb.toString();
        List<IAkLogPrinter> printers =
                config.getLogPrinter() == null ? AkLogManager.getInstance().getPrinters() : config.getLogPrinter();
        for (IAkLogPrinter printer : printers) {
            printer.print(type, tag, result);
        }
    }

    private static String parseBody(@NonNull AkLogConfig config, Object... objects) {
        StringBuilder result = new StringBuilder();
        for (Object object : objects) {
            JsonParser jsonParser = config.getJsonParser();
            if (jsonParser != null) {
                result.append(jsonParser.toJson(object) + ";");
            } else {
                if (object != null) {
                    result.append(object.toString() + ";");
                } else {
                    result.append("null;");
                }
            }
        }
        return FormatUtils.formatBody(result.toString(), AkLogConfig.LINE_MAX_LEN);
    }
}
