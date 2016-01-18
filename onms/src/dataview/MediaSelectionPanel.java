package dataview;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import persistant.WindowControlEnv;

public class MediaSelectionPanel extends JPanel implements ActionListener
{
	private GraphControllerPanel controlPanel;

	private JRadioButton jbSpeedYJZ; // 油浸纸
	private JLabel jlSpeedYJZ;

	private JRadioButton jbSpeedJL; // 交联
	private JLabel jlSpeedJL;

	private JRadioButton jbSpeedJN; // 聚氯
	private JLabel jlSpeedJN;

	private JRadioButton jbSpeedBDL; // 不滴流
	private JLabel jlSpeedBDL;

	private JRadioButton jbSpeedCustomer;// 自定义
	private JLabel jlSpeedCustomer;

	public MediaSelectionPanel(GraphControllerPanel controlPanel)
	{
		super();
		this.controlPanel = controlPanel;
		layoutPanel();
	}

	private void layoutPanel ()
	{
		jbSpeedYJZ = new JRadioButton("油浸纸");// 油浸纸
		jbSpeedYJZ.addActionListener(this);
		jbSpeedYJZ.setSelected(true);
		jlSpeedYJZ = new JLabel("160m/us");

		jbSpeedJL = new JRadioButton("交联");// 交联
		jbSpeedJL.addActionListener(this);
		jlSpeedJL = new JLabel("172m/us");

		jbSpeedJN = new JRadioButton("聚氯");// 聚氯
		jbSpeedJN.addActionListener(this);
		jlSpeedJN = new JLabel("142m/us");

		jbSpeedBDL = new JRadioButton("不滴流");// 不滴流
		jbSpeedBDL.addActionListener(this);
		jlSpeedBDL = new JLabel("144m/us");

		jbSpeedCustomer = new JRadioButton("自定义");// 自定义
		jbSpeedCustomer.addActionListener(this);
		jlSpeedCustomer = new JLabel();

		jbSpeedYJZ.setEnabled(false);// 油浸纸
		jbSpeedJL.setEnabled(false);// 交联
		jbSpeedJN.setEnabled(false);// 聚氯
		jbSpeedBDL.setEnabled(false);// 不滴流
		jbSpeedCustomer.setEnabled(false);// 自定义

		ButtonGroup anotherBG = new ButtonGroup();
		anotherBG.add(jbSpeedYJZ);
		anotherBG.add(jbSpeedJL);
		anotherBG.add(jbSpeedJN);
		anotherBG.add(jbSpeedBDL);
		anotherBG.add(jbSpeedCustomer);

		setLayout(new GridLayout(0, 2));
		add(jbSpeedYJZ);
		add(jlSpeedYJZ);

		add(jbSpeedJL);
		add(jlSpeedJL);

		add(jbSpeedJN);
		add(jlSpeedJN);

		add(jbSpeedBDL);
		add(jlSpeedBDL);

		add(jbSpeedCustomer);
		add(jlSpeedCustomer);
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{
		// *****************************//弹出对话框让用户输入
		if (e.getSource().equals(jbSpeedCustomer))
		{
			double sp = WindowControlEnv.getMediaSpeed();// 把上次的速度记住，当用户没有输入自定义速度时，好恢复
			try
			{
				String inputValue = new String("");
				inputValue = JOptionPane.showInputDialog("请输入传播速度(m/us)", "160");

				if (!(inputValue.equals("") || inputValue.equals(null)))
				{
					WindowControlEnv.setMediaSpeed(Double.parseDouble(inputValue));
					jlSpeedCustomer.setText(inputValue + "m/us");
					controlPanel.getMainFrame().getGraph().repaint();
				}
			}
			catch (Exception ee)
			{
				WindowControlEnv.setMediaSpeed(sp);
				controlPanel.getMainFrame().getGraph().repaint();
			}
		}
		// *****************************选择传播介质
		// 主要就是设置一下传播速度，然后更新图像
		if (e.getSource().equals(jbSpeedYJZ))
		{
			WindowControlEnv.setMediaSpeed(160);
			jlSpeedCustomer.setText("");
			controlPanel.getMainFrame().getGraph().repaint();
		}

		if (e.getSource().equals(jbSpeedJL))
		{
			WindowControlEnv.setMediaSpeed(172);
			jlSpeedCustomer.setText("");
			controlPanel.getMainFrame().getGraph().repaint();
		}

		if (e.getSource().equals(jbSpeedJN))
		{
			WindowControlEnv.setMediaSpeed(142);
			controlPanel.getMainFrame().getGraph().repaint();
		}

		if (e.getSource().equals(jbSpeedBDL))
		{
			WindowControlEnv.setMediaSpeed(144);
			jlSpeedCustomer.setText("");
			controlPanel.getMainFrame().getGraph().repaint();
		}
	}

	public void setCanEnable (boolean isEnable)
	{
		jbSpeedYJZ.setEnabled(isEnable);// 油浸纸
		jbSpeedJL.setEnabled(isEnable);// 交联
		jbSpeedJN.setEnabled(isEnable);// 聚氯
		jbSpeedBDL.setEnabled(isEnable);// 不滴流
		jbSpeedCustomer.setEnabled(isEnable);// 自定义
	}

	public void setDefaultSelection ()
	{
		jbSpeedYJZ.setSelected(true);
	}
}
