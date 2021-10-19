import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class gui extends Thread {
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
        cp.setLayout(null);// 绝对布局

        JLabel texta = new JLabel("MAX");
        texta.setBounds(10, 10, 30, 30);// x, y, 宽, 高
        // 最大值输入文本框
        max.setPreferredSize(new Dimension(100, 35));
        max.setColumns(16);
        max.setBounds(40, 10, 100, 30);// x, y, 宽, 高 

        JComboBox<String> maxjcb = new JComboBox<String>();
        maxjcb.addItem("CPS");
        maxjcb.addItem("MS");
        maxjcb.setEditable(false);
        maxjcb.setBounds(150, 15, 50, 20);// x, y, 宽, 高 

        JLabel texti = new JLabel("MIN");
        texti.setBounds(10, 50, 30, 30);
        // 最小值输入文本框
        min.setPreferredSize(new Dimension(100, 35));// 设置大小
        min.setColumns(16);// 文本框最多可显示内容的列数
        min.setBounds(40, 50, 100, 30);// x, y, 宽, 高 

        JComboBox<String> minjcb = new JComboBox<String>();
        minjcb.addItem("CPS");
        minjcb.addItem("MS");
        minjcb.setEditable(false);
        minjcb.setBounds(150, 55, 50, 20);// x, y, 宽, 高 

        cp.add(texta);
        cp.add(max);
        cp.add(maxjcb);
        cp.add(texti);
        cp.add(min);
        cp.add(minjcb);

        // 滑块
        // 改动的概率
        // slider.getValue() 从 BoundedRangeModel 返回滑块的当前值
        slider.setValue(90);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        slider.setBounds(5, 90, 200, 50);// x, y, 宽, 高 
        cp.add(slider);

        // 添加按钮及按钮事件
        cbutton.setBounds(130, 150, 60, 30);// x, y, 宽, 高 
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
                String smaxjcb = (String) maxjcb.getSelectedItem();
                String sminjcb = (String) minjcb.getSelectedItem();
                cbutton(amax, amin, smaxjcb, sminjcb);
            }
        });

        jf.setVisible(true);// 显示界面
    }

    // 按钮事件--是否要开始判断
    protected void cbutton(String nmax, String nmin, String maxjcb, String minjcb) {
        // 判断是否是非零非负数
        // 原表达式 ^[1-9][0-9]*(\.\d+)?$
        String regex = "^[1-9][0-9]*(\\.\\d+)?$";
        if (nmax.matches(regex) && nmin.matches(regex)) {
            int getmax = Integer.parseInt(nmax);// 强制将String转为int
            int getmin = Integer.parseInt(nmin);
            int probability = 100 - slider.getValue();// 改变间隔的概率

            // 单位转换
            if (maxjcb == "CPS") {
                getmax = 1000 / getmax;// 获得间隔的毫秒数
            } else if (maxjcb == "MS") {
            }
            if (minjcb == "CPS") {
                getmin = 1000 / getmin;
            } else if (minjcb == "MS") {
            }
            if (getmax < getmin) {
                ssarop = true;// 当为false时, 按钮为"开始",反之则"停止"
                max.setEditable(false);// 禁止文本输入框改变文本
                min.setEditable(false);
                crot.start(getmax, getmin, probability);
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