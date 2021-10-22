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

    public static void start(int imax, int imin, int aprobability) throws InterruptedException {
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
                robot.delay(rdelay);// 间隔时间, 使用上面获得的随机数
                gui.information.setText("NOW: " + rdelay + " ms (" + smin + "-" + smax + ")");
            } else if (pbi != 0 && pbi <= probability) {
                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);// 按下左键
                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);// 松开左键
                amount = amount + 1;// 计数器
                System.out.print("-" + np);
                robot.delay(np);// 间隔时间, 使用上面获得的随机数
                gui.information.setText("NOW: " + np + " ms (" + smin + "-" + smax + ")");
            }
        }
    }

    public static int cstop() {
        sors = false;
        System.gc();
        int toamount = amount;
        amount = 0;// 计数器清零
        return toamount;// 返回值
    }
}