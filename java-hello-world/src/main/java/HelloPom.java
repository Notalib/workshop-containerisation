package demo;

import org.tinylog.Logger;
import java.nio.file.*;

public class HelloPom {

    public static void main(String[] args) throws Exception {

        // Enable JSON output for tinylog
        System.setProperty("tinylog.format", "json");

        // Use ENV variable to set name, default to "World".
        String name = System.getenv().getOrDefault("NAME", "World");

        Logger.info("Starting demo application...");
        Logger.info("Hello, {}", name);

        Path messageFile = Paths.get("/data/message.txt");

        try {
            String msg = Files.readString(messageFile);
            Logger.info("Extra message: {}", msg);
        } catch (Exception e) {
            Logger.error("Failed to read message file", e);
        }
    }
}
