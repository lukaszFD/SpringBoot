import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class MouseAutomator {
    public static void main(String[] args) {
        try {
            // Initialize the Robot class
            Robot robot = new Robot();
            Random random = new Random();

            System.out.println("Automation started. Moving mouse...");

            // Simple loop to move and click 5 times
            for (int i = 0; i < 5; i++) {
                int x = random.nextInt(800);
                int y = random.nextInt(600);

                // Move the mouse to specific coordinates
                robot.mouseMove(x, y);
                System.out.println("Moved to: " + x + ", " + y);

                // Press and release the left mouse button
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);

                System.out.println("Click performed.");

                // Wait for 2 seconds before the next action
                Thread.sleep(2000);
            }

            System.out.println("Automation finished.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}