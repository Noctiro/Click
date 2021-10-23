import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class gui extends JFrame implements Runnable {
    static JButton cbutton = new JButton("确定");// 开始/结束 按钮
    static final JTextField max = new JTextField();// 最大值输入框
    static final JTextField min = new JTextField();// 最小值输入框
    static JSlider slider = new JSlider(0, 100);// 概率滑块
    static Boolean ssarop = false;// 开始/结束 判断
    static JLabel information = new JLabel("");

    // 单位
    static JComboBox<String> maxjcb = new JComboBox<String>();
    static JComboBox<String> minjcb = new JComboBox<String>();

    static volatile boolean asors = false; // 控制 开/关
    static int getmax;
    static int getmin;
    static int probability;
    static Thread robot;

    public static void startgui() {
        JFrame jf = new JFrame("CLICK");
        jf.setSize(230, 210);// 窗体大小
        jf.setLocationRelativeTo(null); // 设置窗体居中
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// 关闭窗体事件
        jf.setResizable(true);// 禁止修改大小

        // GridBagLayout
        GridBagLayout cp = new GridBagLayout(); // 实例化布局对象
        jf.setLayout(cp); // jf窗体对象设置为GridBagLayout布局
        GridBagConstraints gbc = new GridBagConstraints();// 实例化这个对象用来对组件进行管理
        // NONE：不调整组件大小。
        // HORIZONTAL：加宽组件，使它在水平方向上填满其显示区域，但是不改变高度。
        // VERTICAL：加高组件，使它在垂直方向上填满其显示区域，但是不改变宽度。
        // BOTH：使组件完全填满其显示区域。

        // 创建菜单栏
        JMenuBar menuBar = new JMenuBar();
        // 创建一级菜单
        JMenu optionMenu = new JMenu("选项");
        JMenu aboutMenu = new JMenu("关于");
        // 一级菜单添加到菜单栏
        menuBar.add(optionMenu);
        menuBar.add(aboutMenu);
        gbc.insets = new Insets(0, 0, 0, 0);// top left bottom right
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;// 当组件没有空间大时，使组件处在北部
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;// 占据本行的所有剩余空间
        gbc.gridheight = 1;
        // jf.add(menuBar);

        gbc.insets = new Insets(2, 5, 2, 5);// top left bottom right

        JLabel texta = new JLabel("MAX");
        gbc.weightx = 10;// 第一列的分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(texta, gbc);
        // 最大值输入文本框
        max.setPreferredSize(new Dimension(100, 35));
        max.setColumns(16);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        cp.setConstraints(max, gbc);

        maxjcb.addItem("CPS");
        maxjcb.addItem("MS");
        maxjcb.setEditable(false);
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(maxjcb, gbc);

        JLabel texti = new JLabel("MIN");
        gbc.weightx = 10;// 分布方式为10%
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(texti, gbc);
        // 最小值输入文本框
        min.setPreferredSize(new Dimension(100, 35));// 设置大小
        min.setColumns(16);// 文本框最多可显示内容的列数
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        cp.setConstraints(min, gbc);

        minjcb.addItem("CPS");
        minjcb.addItem("MS");
        minjcb.setEditable(false);
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.setConstraints(minjcb, gbc);

        // 滑块
        // 改动的概率
        // slider.getValue() 从 BoundedRangeModel 返回滑块的当前值
        slider.setValue(95);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(5);
        slider.setPaintLabels(true);
        slider.setPaintTicks(true);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        cp.setConstraints(slider, gbc);

        // 信息窗
        gbc.weightx = 10;// 分布方式为10%
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        cp.setConstraints(information, gbc);

        // 按钮及按钮事件
        gbc.gridx = 3;
        gbc.gridy = 6;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.gridheight = 1;
        cp.setConstraints(cbutton, gbc);
        cbutton.addActionListener((e) -> {
            if (ssarop) {
                ssarop = false;// 当为false时, 按钮为"开始",反之则"停止"
                crot.cstop();
                robot.interrupt();
                max.setEditable(true);
                min.setEditable(true);
                System.gc();
                cbutton.setText("开始");
            } else {
                String amax = max.getText();
                String amin = min.getText();
                String smaxjcb = (String) maxjcb.getSelectedItem();// 获得选项(选择的单位)
                String sminjcb = (String) minjcb.getSelectedItem();
                cbutton(amax, amin, smaxjcb, sminjcb);
            }
        });

        jf.add(texta);
        jf.add(max);
        jf.add(maxjcb);
        jf.add(texti);
        jf.add(min);
        jf.add(minjcb);
        jf.add(slider);
        jf.add(information);
        jf.add(cbutton);

        jf.setVisible(true);// 显示界面
        parameters();
    }

    // 按钮事件--是否要开始判断
    protected static void cbutton(String nmax, String nmin, String maxjcb, String minjcb) {
        // 判断是否是非零非负数
        // 原表达式 ^[1-9][0-9]*(\.\d+)?$
        String regex = "^[1-9][0-9]*(\\.\\d+)?$";
        if (nmax.matches(regex) && nmin.matches(regex)) {
            getmax = Integer.parseInt(nmax);// 强制将String转为int
            getmin = Integer.parseInt(nmin);
            probability = 100 - slider.getValue();// 改变间隔的概率

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
                max.setEditable(false);// 禁止文本输入框改变文本
                min.setEditable(false);
                cbutton.setText("停止");
                information.setText("倒计时 3 秒");
                ssarop = true;// 当为false时, 按钮为"开始",反之则"停止"
                try {
                    Thread.sleep(3000);
                    information.setText("连点器已开启");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                asors = true;
                robot = new Thread(new gui());
                robot.start();
            } else {
                JOptionPane.showMessageDialog(null, "请核对你输入的最大值和最小值", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "请核对你输入的数值是否正确", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void parameters() {
        int num = 0;
        String regex = "^[1-9][0-9]*(\\.\\d+)?$";
        String dmax = System.getProperty("max");
        String dmin = System.getProperty("min");
        String dp = System.getProperty("p");
        if (dmax != null && dmax.matches(regex)) {
            max.setText(dmax);
            num++;
        }
        if (dmin != null && dmin.matches(regex)) {
            min.setText(dmin);
            num++;
        }
        if (dp != null) {
            int dpt = Integer.parseInt(dp);
            if (dpt > 0 && dpt <= 100) {
                slider.setValue(dpt);
                num++;
            }
        }
        if (num == 3) {
            String amax = max.getText();
            String amin = min.getText();
            String smaxjcb = "MS";
            String sminjcb = "MS";
            cbutton(amax, amin, smaxjcb, sminjcb);
        }
    }

    public void run() {
        // bug
        while (asors) {
            try {
                crot.cstart(getmax, getmin, probability);
                asors = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}