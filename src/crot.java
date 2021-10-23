import java.awt.Robot;
import java.util.Random;
import java.awt.AWTException;
import java.awt.event.InputEvent;

public class crot extends Thread {
    static boolean first = true; // 控制 开/关
    static boolean sors = false; // 控制 开/关
    static int smax;
    static int smin;
    static int np;
    static int probability;
    static int amount = 0;
    static Robot robot;

    public static void cstart(int imax, int imin, int aprobability) throws InterruptedException {
        System.out.println("---------");
        System.out.println("max " + imax + "ms");
        System.out.println("min " + imin + "ms");
        System.out.println("ChangeProbability " + (100 - aprobability) + "%");
        smax = imax;
        smin = imin;
        probability = aprobability;
        sors = true;

        if (first) {
            try {
                robot = new Robot();
            } catch (AWTException e) {
                e.printStackTrace();
            }
            first = false;
        }

        Random r = new Random();
        while (sors) {
            int pbi = r.nextInt(101) + 1;
            if (pbi != 0 && pbi > probability) {
                int rdelay = r.nextInt(smin - smax) + smax;// 最大值为smin 最小值为smax 之间的随机数
                np = rdelay;
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);// 按下左键
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);// 松开左键
                amount = amount + 1;// 计数器
                System.out.print("-" + rdelay);
                gui.information.setText("NOW: " + rdelay + " ms (Amount " + amount + ")");
                robot.delay(rdelay);// 间隔时间, 使用上面获得的随机数\
            } else if (pbi != 0 && pbi <= probability) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);// 按下左键
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);// 松开左键
                amount = amount + 1;// 计数器
                System.out.print("-" + np);
                gui.information.setText("NOW: " + np + " ms (Amount " + amount + ")");
                robot.delay(np);// 间隔时间, 使用上面获得的随机数
            }
        }
    }

    public static void cstop() {
        sors = false;
        gui.information.setText("此次共点击 " + amount + " 下");
        System.out.println("\n此次共点击 " + amount + " 下");
        System.out.println("---------");
        amount = 0;// 计数器清零
        System.gc();
    }
}