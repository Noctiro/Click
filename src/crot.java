import java.awt.Robot;
import java.util.Random;
import java.awt.AWTException;
import java.awt.event.InputEvent;

public class crot extends Thread {
    static int smax;
    static int smin;
    static int amount = 0;
    static Thread robotxc = new Thread(new crot());
    static Robot robot;

    public static void start(int imax, int imin) {
        System.out.println("max " + imax + "ms");
        System.out.println("min " + imin + "ms");
        smax = imax;
        smin = imin;
        robotxc.run();// 启动进程
    }

    public static int cstop() {
        robotxc.interrupt();// 停止进程
        int toamount = amount;
        amount = 0;// 计数器清零
        return toamount;// 返回值
    }

    public void run() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Random r = new Random();
        while (true) {
            int rdelay = r.nextInt(smin - smax) + smax;// 最大值为smin 最小值为smax 之间的随机数
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);// 按下左键
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);// 松开左键
            amount = amount + 1;// 计数器
            System.out.print("-" + rdelay);
            robot.delay(rdelay);// 间隔时间, 使用上面获得的随机数
        }
    }
}