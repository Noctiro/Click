import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class gui extends Thread {
    // 判断str是否是非零非负数
    public static boolean isNumeric(String str) {
        // 原表达式 ^[1-9][0-9]*(\.\d+)?$
        Pattern pattern = Pattern.compile("^[1-9][0-9]*(\\.\\d+)?$");
        return pattern.matcher(str).matches();
    }

    static JButton cbutton = new JButton("确定");
    static final JTextField max = new JTextField("最大值");
    static final JTextField min = new JTextField("最小值");
    static JSlider slider = new JSlider(0, 100);
    static Boolean ssarop = false;

    public void run() {
        JFrame jf = new JFrame("连点器");
        jf.setSize(230, 300);// 窗体大小
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件
        jf.setResizable(true);// 禁止修改大小

        Container cp = jf.getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));// 居左

        JLabel texta = new JLabel("MAX");
        // 最大值输入文本框
        max.setPreferredSize(new Dimension(100, 35));
        max.setColumns(16);

        JLabel texti = new JLabel("MIN");
        // 最小值输入文本框
        min.setPreferredSize(new Dimension(100, 35));// 设置大小
        min.setColumns(16);// 文本框最多可显示内容的列数

        // 占位的按钮(换行用)
        JButton b = new JButton("     ");
        b.setContentAreaFilled(false);
        b.setBorderPainted(false);
        b.setEnabled(false);

        cp.add(texta);
        cp.add(max);
        cp.add(b);
        cp.add(texti);
        cp.add(min);

        // 滑块
        // 改动的概率
        // slider.getValue() 从 BoundedRangeModel 返回滑块的当前值
        slider.setValue(90);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        cp.add(slider);

        // 占位的按钮(换行用)
        JButton c = new JButton("          " + "      ");
        c.setContentAreaFilled(false);
        c.setBorderPainted(false);
        c.setEnabled(false);
        cp.add(c);

        // 添加按钮及按钮事件
        cp.add(cbutton);
        cbutton.addActionListener((e) -> {
            if (ssarop) {
                ssarop = false;// 当为false时, 按钮为"开始",反之则"停止"
                max.setEditable(true);
                min.setEditable(true);
                int amount = crot.cstop();
                System.out.println("这次点击了 " + amount);
                cbutton.setText("开始");
            } else {
                String amax = max.getText();
                String amin = min.getText();
                cbutton(amax, amin);
            }
        });

        jf.setVisible(true);// 显示界面
    }

    // 按钮事件--是否要开始判断
    public void cbutton(String nmax, String nmin) {
        if (isNumeric(nmax) && isNumeric(nmin)) {
            int getmax = Integer.parseInt(nmax);// 强制将String转为int
            int getmin = Integer.parseInt(nmin);
            int probability = 100 - slider.getValue();
            int imax = 1000 / getmax;// 获得间隔的毫秒数
            int imin = 1000 / getmin;
            if (imax < imin) {
                ssarop = true;// 当为false时, 按钮为"开始",反之则"停止"
                max.setEditable(false);// 禁止文本输入框改变文本
                min.setEditable(false);
                crot.start(imax, imin, probability);
                cbutton.setText("停止");
            } else {
                JOptionPane.showMessageDialog(null, "请核对你输入的最大值和最小值", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请核对你输入的数值是否正确", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void createwindow() {
        new gui().start();
    }
}