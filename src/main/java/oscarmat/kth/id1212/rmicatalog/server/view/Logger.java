package oscarmat.kth.id1212.rmicatalog.server.view;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Logs messages to a designated output stream.
 */
public class Logger {

    private static final String ERROR = "ERROR";
    private static final String INFO = "INFO";
    private static final String WARN = "WARN";
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final PrintStream stream;
    private final DateTimeFormatter formatter;

    /**
     * Create a new logger with the assigned output stream.
     * @param out Stream to log messages to.
     */
    public Logger(OutputStream out) {
        stream = new PrintStream(out);
        formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
    }

    /**
     * Log an error message.
     * @param message Contents of the message.
     */
    public void putError(String message) {
        putMessage(message, ERROR);
    }

    public void putInfo(String message) {
        putMessage(message, INFO);
    }

    public void putWarning(String message) {
        putMessage(message, WARN);
    }

    /**
     * Print a message to the log.
     * @param message Contents of the message.
     */
    private void putMessage(String message, String messageType) {
        String timeStamp = getTimeStamp();

        StringBuilder builder = new StringBuilder();
        builder.append("[").append(timeStamp).append("] ");
        builder.append("[").append(messageType).append("] ");
        builder.append(message);

        stream.println(builder.toString());
    }

    /**
     * @return Current timestamp.
     */
    private String getTimeStamp() {
        LocalDateTime dateTime = LocalDateTime.now();
        return dateTime.format(formatter);
    }
}
