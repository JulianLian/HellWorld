package menu;

import action.Action;
import communation.FileDataGetter;
import communation.IDataGetter;
import communation.PortSinfferMocker;
import dataview.CurveSelectionPanel;
import dataview.GraphShowPanel;
import devconfig.DeviceConfigDialog;
import domain.HardWare;
import main.AboutMessage;
import main.Md711MainFrame;
import persistant.InventoryData;
import persistant.PoPDialog;
import persistant.WindowControlEnv;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class MainMenuBar extends JMenuBar implements ActionListener
{
		// 分别是"文件"，"设定"，"通讯"，"属性","帮助"菜单
		private JMenu	fileMenu;
		private JMenu	portSelectMenu;
		private JMenu	 communicateMenu;
		private JMenu	waveAttr;		// 如果是从文件中导入波形，则多一个"波形信息"菜单
		private JMenu	helpMenu;

		// "文件"下面的菜单项
		private JMenuItem	open;
		private JMenuItem	save;
		private JMenuItem	prin; //打印功能隐藏掉了
		private JMenuItem	exit;

		// "设定"下面的菜单项目
		private JMenu					comPortSelectMenu;	// 端口功能也隐藏
		// "端口"下面有4个端口可以选择
		private JRadioButtonMenuItem	com1;
		private JRadioButtonMenuItem	com2;
		private JRadioButtonMenuItem	com3;
		private JRadioButtonMenuItem	com4;
		private ButtonGroup				bg;

		// "通信"菜单的子菜单，点击后便开始通信
		private JMenuItem	beginCommu;		
		private JMenuItem	stopCommu;
		private JMenuItem	commuParamSetting;
		private JMenuItem	devParamSetting;

		// 属性菜单的菜单项
		private JMenuItem lineAttr;

		// "帮助"菜单的子菜单
		private JMenuItem whichCommany;// 公司信息

		private Md711MainFrame	mainFrame;

		private IDataGetter dataGetter;// 用来读取端口数据的类,  有利于用测试接口替换
		private IDataGetter fileDataGetter;

		public MainMenuBar(Md711MainFrame mainFrame)
		{
			initMenuBar();
			this.mainFrame = mainFrame;
		}

		private void initMenuBar()
		{
			//"文件"菜单
			setFileMenu();			
			// "通信"菜单
			setCommunicateMenu();
			//"波形"菜单
			waveAttrMenu();
			//"帮助"菜单
			setHelpMenu();
			//端口选择菜单，这里没有加入主菜单
			setPortSelectionMenu();	
		}

		private void setPortSelectionMenu ()
		{
			portSelectMenu = new JMenu("端口选择(P)");
			portSelectMenu.setMnemonic(KeyEvent.VK_P);
			portSelectMenu.getAccessibleContext().setAccessibleDescription("参数设置");

			// *******************端口选择的下级菜单
			comPortSelectMenu = new JMenu("选择端口");
			bg = new ButtonGroup();
			com1 = new JRadioButtonMenuItem(HardWare.COM1);
			com2 = new JRadioButtonMenuItem(HardWare.COM2);
			com3 = new JRadioButtonMenuItem(HardWare.COM3);
			com4 = new JRadioButtonMenuItem(HardWare.COM4);
			com1.setSelected(true);
			com1.addActionListener(this);
			com2.addActionListener(this);
			com3.addActionListener(this);
			com4.addActionListener(this);
			bg.add(com1);
			bg.add(com2);
			bg.add(com3);
			bg.add(com4);
			comPortSelectMenu.add(com1);
			comPortSelectMenu.add(com2);
			comPortSelectMenu.add(com3);
			comPortSelectMenu.add(com4);

			portSelectMenu.add(comPortSelectMenu);
			
//			add(portSelectMenu);
		}

		private void setHelpMenu ()
		{
			helpMenu = new JMenu("帮助(H)");
			helpMenu.setMnemonic(KeyEvent.VK_H);
			helpMenu.getAccessibleContext().setAccessibleDescription("帮助");
			
			whichCommany = new JMenuItem("关于产品");
			whichCommany.addActionListener(this);
			helpMenu.add(whichCommany);
			
			add(helpMenu);			
		}

		private void waveAttrMenu ()
		{
			waveAttr = new JMenu("属性(A)");
			waveAttr.setMnemonic(KeyEvent.VK_A);
			lineAttr = new JMenuItem("曲线性质");// waveAttr
			waveAttr.add(lineAttr);
			lineAttr.setEnabled(false);
			lineAttr.addActionListener(this);
			add(waveAttr);
		}

		private void setCommunicateMenu ()
		{
			communicateMenu = new JMenu("通信(C)");
			communicateMenu.setMnemonic(KeyEvent.VK_C);
			communicateMenu.getAccessibleContext().setAccessibleDescription("通信");
			add(communicateMenu);
			
			beginCommu = new JMenuItem("开始通信");
			beginCommu.addActionListener(this);
//			communicateMenu.add(beginCommu);
			stopCommu = new JMenuItem("置位");
			stopCommu.addActionListener(this);
			stopCommu.setEnabled(true);
//			stopCommu.setEnabled(false);
			communicateMenu.add(stopCommu);
			
			JMenuItem commuAddrSetting = new JMenuItem("通讯参数...");
			commuAddrSetting.setActionCommand("commuAddrSetting");
			commuAddrSetting.addActionListener(this);			
			communicateMenu.add(commuAddrSetting);
			
			commuParamSetting = new JMenuItem("查询参数...");
			commuParamSetting.addActionListener(this);			
			communicateMenu.add(commuParamSetting);
			
			devParamSetting = new JMenuItem("设备参数...");
			devParamSetting.addActionListener(this);			
			communicateMenu.add(devParamSetting);
		}

		private void setFileMenu ()
		{
			fileMenu = new JMenu("文件(F)");
			
			fileMenu.setMnemonic(KeyEvent.VK_F);
			fileMenu.getAccessibleContext().setAccessibleDescription("文件");
			add(fileMenu);
			
			open = new JMenuItem("打开");
			open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
			open.getAccessibleContext().setAccessibleDescription("打开已经存储的数据");
			open.addActionListener(this);
			fileMenu.add(open);

			save = new JMenuItem("保存");
			save.setEnabled(false);
			save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
			save.addActionListener(this);
			fileMenu.add(save);

			prin = new JMenuItem("打印");
			prin.setEnabled(false);
			prin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
			prin.addActionListener(this);
//			fileMenu.add(prin);

			fileMenu.addSeparator();// 加上分割线
			exit = new JMenuItem("退出");
			exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
			exit.addActionListener(this);
			fileMenu.add(exit);
		}

		public void setCurAttrEnable()
		{
			lineAttr.setEnabled(false);
		}

		public JMenuItem getPrin()
		{
			return prin;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			// 端口选择事件
			if (e.getSource().equals(com1))
				mainFrame.setPort(HardWare.COM1);

			else if (e.getSource().equals(com2))
				mainFrame.setPort(HardWare.COM2);

			else if (e.getSource().equals(com3))
				mainFrame.setPort(HardWare.COM3);
			else if (e.getSource().equals(com4))
				mainFrame.setPort(HardWare.COM4);

			// 文件－退出
			else if (e.getSource().equals(exit))
			{
				System.exit(0);
			}
			// 帮助－关于
			else if (e.getSource().equals(whichCommany))
			{
				JOptionPane.showMessageDialog(mainFrame, "谢谢使用我们的产品", "帮助", JOptionPane.PLAIN_MESSAGE);
			}	
			
			// 通讯－开始通讯,   这个功能被屏蔽掉了
//			else if (e.getSource().equals(beginCommu))
//			{
//				beginCommu.setEnabled(false);
//				stopCommu.setEnabled(true);
//				fetchWaveShapeDataFromDev();
//			}			
			else if (e.getActionCommand().equals("commuAddrSetting"))
			{
				communation.CommuAddrConfigPanel.showDialog(mainFrame);
			}
			else if (e.getSource().equals(this.commuParamSetting))
			{
				communation.CommuParamConfigDialog.showDialog(mainFrame);
			}
			else if (e.getSource().equals(this.devParamSetting))
			{
				DeviceConfigDialog.showDialog(mainFrame);
			}

			// **************打印按钮
			else if (e.getSource().equals(prin))
			{
				Action.setPrintAction(mainFrame);
			}

			// ****************************点击开始复位
			// 让面板上的所有元素复原，同时把已经生成的对象销毁
			else if (e.getSource().equals(stopCommu))
			{
				if (dataGetter != null)
				{
					dataGetter.stopFetchData();
					dataGetter = null;
				}
				InventoryData.clearPersistData();
				WindowControlEnv.toDefaultParam();				
				
				GraphShowPanel.setNotBeginMeasureState();
				this.clearAll();
				mainFrame.getGraphControllerpanel().clearAll();
				PoPDialog saveDialog = mainFrame.getSaveDialog();
				if (saveDialog != null)
					saveDialog.clearAll();
				try
				{
					AboutMessage am = mainFrame.getAm();
					if (am != null)
							am.clearAll();// AboutMessage
				}
				catch (Exception eee)
				{
					System.err.println("错误");
				}
				mainFrame.getGraph().repaint();
			}
			// ********************************* 保存事件
			else if (e.getSource().equals(save))
			{
				mainFrame.saveAction();
				prin.setEnabled(true);
			}
			// ******************************打开文件事件
			// 这里面FileInputStream还没有关闭
			else if (e.getSource().equals(open))
			{
				fileDataGetter = new  FileDataGetter(mainFrame);
				boolean isSuccess = fileDataGetter.startFetchData();
				if(isSuccess)
				{
					mainFrame.getGraphControllerpanel().getCurSelectionPanel()
							.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, true);
					lineAttr.setEnabled(true);
					prin.setEnabled(true);
					save.setEnabled(false);
					stopCommu.setEnabled(true);
				}
			}
			else if (e.getSource().equals(lineAttr))
			{
				mainFrame.curAttrAction();
			}
		}

		public void fetchWaveShapeDataFromDev ()
		{			
			// 由这个类去完成数据采集任务
			createDataGetter();
			if(dataGetter.startFetchData())
			{
				mainFrame.getGraphControllerpanel().getCurSelectionPanel()
						.setStateEnable(CurveSelectionPanel.PORT_CUR_SELECTION, true);
			}
		}

		private void createDataGetter()
		{
			dataGetter = new PortSinfferMocker( mainFrame);		
		}

		public void jbRedSelectAction()
		{
			mainFrame.getGraphControllerpanel().getCurSelectionPanel().selectPortDataLine();// 表示用户此时选择了端口线条
			lineAttr.setEnabled(false);
			save.setEnabled(true);
			if (WindowControlEnv.getPortDataHaveSaved())
				prin.setEnabled(true);
			else
				prin.setEnabled(false);
		}

		public void jbGreenSelectAction()
		{
			mainFrame.getGraphControllerpanel().getCurSelectionPanel().selectFileDataLine();
			lineAttr.setEnabled(true);
			prin.setEnabled(true);
			save.setEnabled(false);
		}

		public void setSaveItemState(boolean isEnable)
		{
			save.setEnabled(isEnable);
		}

		public void clickStopCommuItem()
		{
			stopCommu.doClick();
		}

		private void clearAll()
		{
			lineAttr.setEnabled(false);
			save.setEnabled(false);
			prin.setEnabled(false);
			com1.setSelected(true);
			com2.setSelected(false);
			com3.setSelected(false);
			com4.setSelected(false);
			beginCommu.setEnabled(true);
			stopCommu.setEnabled(true);
//			stopCommu.setEnabled(false);
			prin.setEnabled(false);
			save.setEnabled(false);
		}
}
