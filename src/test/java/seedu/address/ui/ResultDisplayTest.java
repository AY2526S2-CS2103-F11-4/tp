package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.concurrent.CountDownLatch;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Platform;

public class ResultDisplayTest {

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        System.setProperty("java.awt.headless", "true");
        System.setProperty("testfx.robot", "glass");
        System.setProperty("testfx.headless", "true");
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");

        CountDownLatch latch = new CountDownLatch(1);
        try {
            Platform.startup(latch::countDown);
        } catch (IllegalStateException e) {
            latch.countDown();
        }
        latch.await();
    }

    @Test
    public void setFeedbackToUser_normalMessage_success() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser("Hello"));
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void setFeedbackToUser_afterExitMessage_removesExitStyle() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ResultDisplay resultDisplay = new ResultDisplay();
            resultDisplay.setExitMessage("Bye");
            assertDoesNotThrow(() -> resultDisplay.setFeedbackToUser("Hello again"));
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void setExitMessage_normalMessage_success() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ResultDisplay resultDisplay = new ResultDisplay();
            assertDoesNotThrow(() -> resultDisplay.setExitMessage("Goodbye!"));
            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void setExitMessage_calledTwice_doesNotDuplicateStyle() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            ResultDisplay resultDisplay = new ResultDisplay();
            resultDisplay.setExitMessage("Bye");
            assertDoesNotThrow(() -> resultDisplay.setExitMessage("Bye again"));
            latch.countDown();
        });
        latch.await();
    }
}