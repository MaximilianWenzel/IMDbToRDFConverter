package util;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleUtils {

    // https://logging.apache.org/log4j/2.x/manual/layouts.html
    private static final Logger log;
    private static String SEPARATOR = "-------------------------------------------------------------";

    static {
        log = Logger.getLogger("logFile.txt");
        log.setUseParentHandlers(false);

        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LogFormatter());
        handler.setLevel(Level.FINE);

        log.addHandler(handler);

        log.setLevel(Level.FINE);
    }

    public static Logger getLogger() {
        return log;
    }

    public static String getSeparator() {
        return SEPARATOR;
    }
}
