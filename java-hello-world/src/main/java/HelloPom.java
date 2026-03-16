package demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import java.nio.file.*;

public class HelloPom {

    private static final Logger logger = LoggerFactory.getLogger(HelloPom.class);

    public static void main(String[] args) throws Exception {

        // Override the logger Level.
        ((ch.qos.logback.classic.Logger) logger).setLevel(Level.INFO);

        // Use ENV variable to set name, default to "World".
        String name = System.getenv().getOrDefault("NAME", "World");

        logger.info("Starting demo application...");
        logger.info("Hello, {}", name);

        Path messageFile = Paths.get("/data/message.txt");

        try {
            String msg = Files.readString(messageFile);
            logger.info("Extra message: {}", msg);
        } catch (Exception e) {
            logger.error("Failed to read message file", e);
        }
    }
}
