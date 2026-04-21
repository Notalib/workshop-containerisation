package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import java.nio.file.*;

public class HelloGradleApp {

    private static final Logger logger = LoggerFactory.getLogger(HelloGradleApp.class);

    public static void main(String[] args) throws Exception {

        // Override the logger Level.
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO);

        // Use ENV variable to set name, default to "World".
        String name = System.getenv().getOrDefault("NAME", "Worldddd");
        String messagePath = System.getenv().getOrDefault("MESSAGE_PATH", "/data/message.txt");

        logger.info("Starting demo application...");
        logger.info("Hello, {}", name);

        String msg = new HelloGradleApp().getMessage(messagePath);
        logger.info("Extra message: {}", msg);
    }

    public String getMessage(String messagePath) {
        Path messageFile = Paths.get(messagePath);

        try {
            String msg = Files.readString(messageFile);
            return msg;
        } catch (Exception e) {
            logger.error("Failed to read message file", e);
            return "[NO MESSAGE]";
        }
    }
}
