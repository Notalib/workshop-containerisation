package demo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    @Test void appHasAGreeting() {
        // Setup
        HelloGradleApp classUnderTest = new HelloGradleApp();

        // Act
        String message = classUnderTest.getMessage("/dummy-path");

        // Assert
        assertNotNull(message, "app should have a greeting");
        assertEquals(message, "[NO MESSAGE]", "app should return the expected message");
    }
}
