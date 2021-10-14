package com.lq.library.log;

import com.lq.library.log.printer.IAkLogPrinter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description:
 * @author: mick
 * @CreateAt: 8/30/21 4:12 PM
 * @UpdateUser: mick
 * @UpdateDate: 8/30/21 4:12 PM
 * @UpdateRemark:
 */
public class AkLogManager {
    private static AkLogManager instance;
    private final List<IAkLogPrinter> printers = new ArrayList();
    private AkLogConfig logConfig;

    private AkLogManager(AkLogConfig logConfig, IAkLogPrinter[] printers) {
        this.logConfig = logConfig;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static AkLogManager getInstance() {
        return instance;
    }

    public static void init(AkLogConfig akLogConfig, IAkLogPrinter... printers) {
        instance = new AkLogManager(akLogConfig, printers);
    }

    public AkLogConfig getLogConfig() {
        return logConfig;
    }

    public List<IAkLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(IAkLogPrinter printer) {
        this.printers.add(printer);
    }

    public void removePrinter(IAkLogPrinter printer) {
        this.printers.remove(printer);
    }
}
