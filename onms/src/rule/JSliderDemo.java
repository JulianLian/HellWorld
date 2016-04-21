package rule;

import java.awt.*;
import java.util.Dictionary;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.event.*;

public class JSliderDemo extends JFrame {

    /**
     * source code from ��java���ļ��� ��1 ����֪ʶ�� P340
     */

    int DEFAULT_WIDTH = 350;
    int DEFAULT_HEIGHT = 450;
    private JPanel sliderPanel;
    private JTextField textField;
    private ChangeListener changeListener;// ������

    public JSliderDemo() {
        setTitle("JSliderDemo");
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        // ����һ������������Ӧ�¼�
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

        // ���sliderPanel����������JSlider
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
        slider.setPaintTicks(true);// ��ʾ���
        slider.setMajorTickSpacing(20); // 20һ���
        slider.setMinorTickSpacing(5);// 5һС��
        addSlider(slider, "Ticks");

        // add SnapToTicks slider
        slider = new JSlider();
        slider.setPaintTicks(true);// ��ʾ���
        slider.setSnapToTicks(true);//ǿ�ƶ��뵽���
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(5);// 5һС��
        addSlider(slider, "SnapToTicks");

        // add NoTrack slider
        slider = new JSlider();
        slider.setPaintTicks(true);// ��ʾ���
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(5);// 5һС��
        slider.setPaintTrack(false);//����ʾ����
        addSlider(slider, "NoTrack");

        // add InvertedSlider
        slider = new JSlider();
        slider.setPaintTicks(true);// ��ʾ���
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(5);// 5һС��
        slider.setInverted(true);//��תslider����
        addSlider(slider, "InvertedSlider");

        // add Slider with labels
        slider = new JSlider();
        slider.setPaintTicks(true);// ��ʾ���
        slider.setPaintLabels(true);//������ֱ�ǩ
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(5);// 5һС��
        addSlider(slider, "Labels");

        // add Slider with alphabetic labels
        slider = new JSlider();
        slider.setPaintTicks(true);// ��ʾ���
        slider.setPaintLabels(true);
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(5);// 5һС��

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
        slider.setPaintTicks(true);// ��ʾ���
        slider.setPaintLabels(true);
        slider.setSnapToTicks(true);
        slider.setMajorTickSpacing(20);// 20һ���
        slider.setMinorTickSpacing(20);// 20һС��

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
     * ���һ��slider�����󶨼�����
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
        // �������岢ָ������
        JSliderDemo frame = new JSliderDemo();
        // �رմ�����˳�����
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // �Զ��������пؼ���С
        // frame.pack();
        // ���ô���λ������Ļ����
        frame.setLocationRelativeTo(null);
        // ��ʾ����
        frame.setVisible(true);
    }

}