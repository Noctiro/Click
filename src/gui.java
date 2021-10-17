import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

public class gui extends Thread {
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
        jf.setSize(300, 400);
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setResizable(true);// 禁止修改大小

        Container cp = jf.getContentPane();
        cp.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 10));

        max.setPreferredSize(new Dimension(100, 35));
        max.setColumns(32);

        min.setPreferredSize(new Dimension(100, 35));// 设置大小
        min.setColumns(32);// 文本框最多可显示内容的列数
        cp.add(max);
        cp.add(min);

        // 滑块
        // getValue() 从 BoundedRangeModel 返回滑块的当前值
        slider.setValue(50);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        cp.add(slider);

        JButton c = new JButton("          " + "      ");
        c.setContentAreaFilled(false);
        c.setBorderPainted(false);
        c.setEnabled(false);
        cp.add(c);

        cp.add(cbutton);
        cbutton.addActionListener((e) -> {
            if (ssarop) {
                ssarop = false;
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

        jf.setVisible(true);
    }

    public void cbutton(String nmax, String nmin) {
        if (isNumeric(nmax) && isNumeric(nmin)) {
            int getmax = Integer.parseInt(nmax);
            int getmin = Integer.parseInt(nmin);
            int imax = 1000 / getmax;
            int imin = 1000 / getmin;
            if (imax < imin) {
                ssarop = true;
                max.setEditable(false);
                min.setEditable(false);
                crot.start(imax, imin);
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