import java.nio.file.*;
import java.io.IOException;

public class Hello {
    public static void main(String[] args) throws IOException {

        String name = System.getenv().getOrDefault("NAME", "World");

        System.out.println("Hello, " + name + "!");

        Path messageFile = Paths.get("/data/message.txt");

        if (Files.exists(messageFile)) {
            System.out.println("Extra message: " + Files.readString(messageFile));
        } else {
            System.out.println("No extra message file found.");
        }
    }
}
