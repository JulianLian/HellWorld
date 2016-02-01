package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import action.Action;
import communation.FileDataGetter;
import communation.IDataGetter;
import communation.dialog.CommuAddrConfigDialog;
import communation.dialog.CommuParamConfigDialog;
import dataview.Constant;
import dataview.CurveSelectionPanel;
import dataview.GraphShowPanel;
import devconfig.DeviceConfigDialog;
import env.MDLogger;
import main.AboutMessage;
import main.Md711MainFrame;
import persistant.InventoryData;
import persistant.PoPDialog;
import persistant.WindowControlEnv;

public class MainMenuBar extends JMenuBar implements ActionListener
{
	private static final long serialVersionUID = 1L;
	// 分别是"文件"，"设定"，"通讯"，"属性","帮助"菜单
	private JMenu fileMenu;
	private JMenu communicateMenu;
	private JMenu waveAttr; // 如果是从文件中导入波形，则多一个"波形信息"菜单
	private JMenu helpMenu;
	
	// "文件"下面的菜单项
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem prin; // 打印功能隐藏掉了
	private JMenuItem exit;
	
	// "设定"下面的菜单项目
	
	// "通信"菜单的子菜单，点击后便开始通信
	private JMenuItem beginCommu;
	private JMenuItem resetMenuItem;
	private JMenuItem commuParamSetting;
//	private JMenuItem devParamSetting;
	
	// 属性菜单的菜单项
	private JMenuItem lineAttr;
	
	// "帮助"菜单的子菜单
	private JMenuItem whichCommany;// 公司信息
	
	private Md711MainFrame mainFrame;
	
	public MainMenuBar(Md711MainFrame mainFrame)
	{
		initMenuBar();
		this.mainFrame = mainFrame;
	}
	
	private void initMenuBar ()
	{
		// "文件"菜单
		setFileMenu();
		// "通信"菜单
		setCommunicateMenu();
		// "波形"菜单
		waveAttrMenu();
		// "帮助"菜单
		setHelpMenu();
	}
	
	private void setHelpMenu ()
	{
		helpMenu = new JMenu("帮助(H)");		
		helpMenu.setMnemonic(KeyEvent.VK_H);
		helpMenu.getAccessibleContext().setAccessibleDescription("帮助");
		
		whichCommany = new JMenuItem("关于产品");
		whichCommany.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		whichCommany.getAccessibleContext().setAccessibleDescription("帮助");
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
//		add(waveAttr);
	}
	
	private void setCommunicateMenu ()
	{
		communicateMenu = new JMenu("通信(C)");
		communicateMenu.setMnemonic(KeyEvent.VK_C);
		communicateMenu.getAccessibleContext().setAccessibleDescription("通信");
		add(communicateMenu);
		
		beginCommu = new JMenuItem("开始通信");
		beginCommu.addActionListener(this);
		// communicateMenu.add(beginCommu);
		resetMenuItem = new JMenuItem("置位",Constant.createImageIcon("restore.png"));
		resetMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		resetMenuItem.getAccessibleContext().setAccessibleDescription("恢复初始状态");
		resetMenuItem.addActionListener(this);
		resetMenuItem.setEnabled(true);
		// stopCommu.setEnabled(false);
		communicateMenu.add(resetMenuItem);
		
		JMenuItem commuAddrSetting = new JMenuItem("通讯端口...",Constant.createImageIcon("communication.png"));
		commuAddrSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
		commuAddrSetting.getAccessibleContext().setAccessibleDescription("通讯端口设置");
		commuAddrSetting.setActionCommand("commuAddrSetting");
		commuAddrSetting.addActionListener(this);
		communicateMenu.add(commuAddrSetting);
		
		commuParamSetting = new JMenuItem("查询参数...", Constant.createImageIcon("start.png"));
		commuParamSetting.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
		commuParamSetting.getAccessibleContext().setAccessibleDescription("获取光纤数据");
		commuParamSetting.addActionListener(this);
		communicateMenu.add(commuParamSetting);
		
//		devParamSetting = new JMenuItem("设备参数...");
//		devParamSetting.addActionListener(this);
		// communicateMenu.add(devParamSetting);
	}
	
	private void setFileMenu ()
	{
		fileMenu = new JMenu("文件(F)");
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.getAccessibleContext().setAccessibleDescription("文件");
		add(fileMenu);
		
		open = new JMenuItem("打开",Constant.createImageIcon("open.png"));
		open.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		open.getAccessibleContext().setAccessibleDescription("打开已经存储的数据");
		open.addActionListener(this);
		fileMenu.add(open);
		
		save = new JMenuItem("保存",Constant.createImageIcon("save.png"));
		save.setEnabled(false);
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		save.addActionListener(this);
		fileMenu.add(save);
		
		prin = new JMenuItem("打印");
		prin.setEnabled(false);
		prin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
		prin.addActionListener(this);
		// fileMenu.add(prin);
		
		fileMenu.addSeparator();// 加上分割线
		exit = new JMenuItem("退出",Constant.createImageIcon("exit.png"));
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
		exit.addActionListener(this);
		fileMenu.add(exit);
	}
	
	public void setCurAttrEnable ()
	{
		lineAttr.setEnabled(false);
	}
	
	public JMenuItem getPrin ()
	{
		return prin;
	}
	
	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource().equals(exit))
		{
			System.exit(0);
		}
		// 帮助－关于
		else if (e.getSource().equals(whichCommany))
		{
			JOptionPane.showMessageDialog(mainFrame, "谢谢使用我们的产品", "帮助", JOptionPane.PLAIN_MESSAGE);
		}
		
		// 通讯－开始通讯, 这个功能被屏蔽掉了
		// else if (e.getSource().equals(beginCommu))
		// {
		// beginCommu.setEnabled(false);
		// stopCommu.setEnabled(true);
		// fetchWaveShapeDataFromDev();
		// }
		else if (e.getActionCommand().equals("commuAddrSetting"))
		{
			CommuAddrConfigDialog.showDialog(mainFrame);
		}
		else if (e.getSource().equals(this.commuParamSetting))
		{
			CommuParamConfigDialog.showDialog(mainFrame);
		}
//		else if (e.getSource().equals(this.devParamSetting))
//		{
//			DeviceConfigDialog.showDialog(mainFrame);
//		}
		
		// **************打印按钮
		else if (e.getSource().equals(prin))
		{
			Action.setPrintAction(mainFrame);
		}
		
		// ****************************点击开始复位
		// 让面板上的所有元素复原，同时把已经生成的对象销毁
		else if (e.getSource().equals(resetMenuItem))
		{
			reset();
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
			IDataGetter fileDataGetter = getFileDataGetter();
			List<Double> waveData = fileDataGetter.getWaveData(null);
			if (waveData != null)
			{
				// mainFrame.getGraphControllerpanel().getCurSelectionPanel().selectFileDataLine();
				CurveSelectionPanel.selectFileDataLine();
				WindowControlEnv.setRepaintForFileInfoCome(true);
				mainFrame.getGraphControllerpanel().setStateWhenOpenFile();
				mainFrame.showFileGraph();
				mainFrame.getGraphControllerpanel().getCurSelectionPanel()
						.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, true);
				lineAttr.setEnabled(true);
				prin.setEnabled(true);
				save.setEnabled(false);
				resetMenuItem.setEnabled(true);
			}
			else
			{
				mainFrame.getGraphControllerpanel().getMoveAndAmplyPanel().setStepEnable(false);
			}
		}
		else if (e.getSource().equals(lineAttr))
		{
			mainFrame.curAttrAction();
		}
	}
	
	public void reset ()
	{
		InventoryData.clearPersistData();
		WindowControlEnv.toDefaultParam();
		
		GraphShowPanel.setNotBeginMeasureState();
		this.clearAll();
		mainFrame.getGraphControllerpanel().clearAll();
		mainFrame.getEventPanel().clearKeyPointData();
		mainFrame.getEventPanel().clearQueryPropertyData();
		PoPDialog saveDialog = mainFrame.getSaveDialog();
		if (saveDialog != null)
			saveDialog.clearAll();
		try
		{
			AboutMessage am = mainFrame.getAm();
			if (am != null)
				am.clearAll();// AboutMessage
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
		}
		mainFrame.getGraph().repaint();
	}
	
	private IDataGetter getFileDataGetter ()
	{
		return new FileDataGetter(mainFrame);
	}
	
	public void jbRedSelectAction ()
	{
		CurveSelectionPanel.selectPortDataLine();// 表示用户此时选择了端口线条
		lineAttr.setEnabled(false);
		save.setEnabled(true);
		if (WindowControlEnv.getPortDataHaveSaved())
			prin.setEnabled(true);
		else
			prin.setEnabled(false);
	}
	
	public void jbGreenSelectAction ()
	{
		CurveSelectionPanel.selectFileDataLine();
		lineAttr.setEnabled(true);
		prin.setEnabled(true);
		save.setEnabled(false);
	}
	
	public void setSaveItemState (boolean isEnable)
	{
		save.setEnabled(isEnable);
	}
	
	public void clickStopCommuItem ()
	{
		resetMenuItem.doClick();
	}
	
	private void clearAll ()
	{
		lineAttr.setEnabled(false);
		save.setEnabled(false);
		prin.setEnabled(false);
		beginCommu.setEnabled(true);
		resetMenuItem.setEnabled(true);
		// stopCommu.setEnabled(false);
		prin.setEnabled(false);
		save.setEnabled(false);
	}
}

