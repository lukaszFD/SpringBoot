import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Random;

public class ExcelAutomator {
    private static final Random random = new Random();

    public static void main(String[] args) {
        try {
            Robot robot = new Robot();
            // Optional: short delay to allow you to switch to Excel window
            System.out.println("Starting in 5 seconds... Open Excel now.");
            Thread.sleep(5000);

            for (int i = 0; i < 10; i++) {
                int x = random.nextInt(800) + 100; // Offset to avoid edges
                int y = random.nextInt(600) + 100;

                robot.mouseMove(x, y);

                if (random.nextBoolean()) {
                    // LEFT CLICK - Typically selects a cell in Excel
                    System.out.println("Left click at (" + x + "," + y + ")");
                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                } else {
                    // RIGHT CLICK + TEXT INPUT
                    System.out.println("Right click + typing at (" + x + "," + y + ")");
                    robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);

                    // Wait a bit for the UI to react
                    Thread.sleep(500);

                    // Typing "HELLO" as an example
                    typeString(robot, "HELLO FROM JAVA");
                }

                sleepRandomly(2, 15);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to type strings using Robot class
     */
    private static void typeString(Robot robot, String text) {
        for (char c : text.toCharArray()) {
            int keyCode = KeyEvent.getExtendedKeyCodeForChar(c);
            if (KeyEvent.CHAR_UNDEFINED == keyCode) {
                continue;
            }
            robot.keyPress(keyCode);
            robot.keyRelease(keyCode);
            // Small delay between keystrokes to look natural
            robot.delay(50);
        }
        // Press Enter to confirm entry in Excel
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }

    private static void sleepRandomly(int minSeconds, int maxSeconds) {
        try {
            int randomSeconds = random.nextInt((maxSeconds - minSeconds) + 1) + minSeconds;
            System.out.println("Next action in " + randomSeconds + "s...");
            Thread.sleep(randomSeconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}