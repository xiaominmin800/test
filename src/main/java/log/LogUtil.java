package log;

import java.util.logging.Logger;

public class LogUtil {

    public static Logger logger() {
        Logger log = Logger.getLogger(String.valueOf(LogUtil.class));
        return log;
    }

}

