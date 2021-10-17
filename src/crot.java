import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

public class crot extends Thread {
    static int smax;
    static int smin;
    static int amount = 0;
    static Thread robotxc = new Thread(new crot());
    static Robot robot;

    public static void start(int imax, int imin) {
        System.out.println("max " + imax);
        System.out.println("min " + imin);
        smax = imax;
        smin = imin;
        robotxc.run();
    }

    public static int cstop() {
        robotxc.interrupt();
        int toamount = amount;
        amount = 0;
        return toamount;
    }

    public void run() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        while (true) {
            int rdelay = r.nextInt(smin - smax) + smax;
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            amount = amount + 1;
            System.out.print("-" + rdelay);
            robot.delay(rdelay);
        }
    }
}