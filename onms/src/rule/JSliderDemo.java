package rule;

import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.*;

public class JSliderDemo extends JFrame {

    /**
     * source code from 《java核心技术 卷1 基础知识》 P340
     */

    int DEFAULT_WIDTH = 350;
    int DEFAULT_HEIGHT = 450;
    private JPanel sliderPanel;
    private JTextField textField;
    private ChangeListener changeListener;// 监听器

    public JSliderDemo() {
        setTitle("JSliderDemo");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // 构造一个监听器，响应事件
        changeListener = new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                System.out.println("stateChanged called");
                // update textField when the slider value changes
                if (event.getSource() instanceof JSlider) {
                    JSlider source = (JSlider) event.getSource();
                    textField.setText("" + source.getValue());
                    System.out.println(source.getValue());
                }
            }
        };

        // 添加sliderPanel，它包含个JSlider
        sliderPanel = new JPanel();
        sliderPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // add textField that displays the slider value
        textField = new JTextField();
        add(sliderPanel, BorderLayout.CENTER);
        add(textField, BorderLayout.SOUTH);

        // add Plain slider
        JSlider slider = new JSlider();
        addSlider(slider, "Plain");

        // add Ticks slider
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setMajorTickSpacing(20); // 20一大格
        slider.setMinorTickSpacing(5);// 5一小格
        addSlider(slider, "Ticks");

        // add SnapToTicks slider
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setSnapToTicks(true);//强制对齐到标尺
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(5);// 5一小格
        addSlider(slider, "SnapToTicks");

        // add NoTrack slider
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(5);// 5一小格
        slider.setPaintTrack(false);//不显示数轴
        addSlider(slider, "NoTrack");

        // add InvertedSlider
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(5);// 5一小格
        slider.setInverted(true);//反转slider方向
        addSlider(slider, "InvertedSlider");

        // add Slider with labels
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setPaintLabels(true);//添加数字标签
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(5);// 5一小格
        addSlider(slider, "Labels");

        // add Slider with alphabetic labels
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(5);// 5一小格

        Dictionary<Integer, Component> labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel("A"));
        labelTable.put(20, new JLabel("B"));
        labelTable.put(40, new JLabel("C"));
        labelTable.put(60, new JLabel("D"));
        labelTable.put(80, new JLabel("E"));
        labelTable.put(100, new JLabel("F"));

        slider.setLabelTable(labelTable);       
        addSlider(slider, "CustomLabels");

        // add IconsSlider
        slider = new JSlider();
        slider.setPaintTicks(true);// 显示标尺
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);// 20一大格
        slider.setMinorTickSpacing(20);// 20一小格

        labelTable = new Hashtable<Integer, Component>();
        labelTable.put(0, new JLabel(new ImageIcon("nine.gif")));
        labelTable.put(20, new JLabel(new ImageIcon("ten.gif")));
        labelTable.put(40, new JLabel(new ImageIcon("jack.gif")));
        labelTable.put(60, new JLabel(new ImageIcon("queen.gif")));
        labelTable.put(80, new JLabel(new ImageIcon("king.gif")));
        labelTable.put(100, new JLabel(new ImageIcon("ace.gif")));

        slider.setLabelTable(labelTable);
        addSlider(slider, "IconLabels");

    }

    /*
     * 添加一个slider，并绑定监听器
     */
    private void addSlider(JSlider slider, String description) {
        slider.addChangeListener(changeListener);
        JPanel panel = new JPanel();
        panel.add(slider);
        panel.add(new JLabel(description));
        sliderPanel.add(panel);
        System.out.println("addSlider called");
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // 创建窗体并指定标题
        JSliderDemo frame = new JSliderDemo();
        // 关闭窗体后退出程序
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 自动适配所有控件大小
        // frame.pack();
        // 设置窗体位置在屏幕中央
        frame.setLocationRelativeTo(null);
        // 显示窗体
        frame.setVisible(true);
    }

}