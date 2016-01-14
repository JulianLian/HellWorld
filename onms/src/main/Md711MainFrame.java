
package main;
/*
 * 作者：         杨安印
 * 开始日期：     2005-8-8
 * 预期完成时间： 40天
 */

import action.Action;
import dataview.CurveSelectionPanel;
import dataview.GraphControllerPanel;
import dataview.GraphShowPanel;
import domain.HardWare;
import domain.ProductInfo;
import menu.MainMenuBar;
import persistant.PoPDialog;
import persistant.SaveWithoughtConfirmPoPDialog;
import persistant.WindowControlEnv;

import javax.swing.*;
import java.awt.*;

/*
 *
 * 本程序意在做一个通讯类软件，用图形化的界面来和用户交互
 * 主要功能是从串口以9600bps的速率来获取波形数据，
 * 然后在界面中用图形来显示；同时可以设定波形显示的方法，比如
 * 用哪个com口，放大或缩小波形，对已经获取的数据的采样步长，
 * 上下左右移动图形；同时能够显示多个波形，方便用户对比它们；
 * 同时用户可以打印这次的波形以及波形数据，能够把界面上的波形
 * 存储到文件中；用户也能够调用历史数据进行显示等等处理
 *
 *-Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=8000
 */

public class Md711MainFrame extends JFrame
{
	private String whichCommPort = HardWare.COM1;// 默认为com1端口
	// ****************************************
	private GraphShowPanel graph; // 显示图形的面板类
	private AboutMessage am; // 用户点击后会显示文件数据的信息
	private PoPDialog saveDialog; // 用户在这里填入保存的文件的信息

	// **********************************************菜单选项定义
	private MainMenuBar checkerMenuBar;

	private ControlAreaJTabbedPanel controlJtabbedPanel;

	public Md711MainFrame()
	{
		action.Action.closingAction(this);

		initMenuBar();
		initGraphicControllerPanel();

		layoutPanel();
	}

	private void layoutPanel ()
	{
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
		JTabbedPane graphScrollPane = initDataShowPanel();
		splitPane.setLeftComponent(graphScrollPane);
		// JScrollPane graphScrollPane = new
		// JScrollPane(initDataShowPanel());
		// splitPane.setLeftComponent(graphScrollPane);
		JScrollPane controlPanel = new JScrollPane(controlJtabbedPanel);
		controlPanel.setMaximumSize(new Dimension(controlPanel.getPreferredSize().width, 100));
		splitPane.setRightComponent(controlPanel);
		graphScrollPane.setMinimumSize(new Dimension(800, 600));
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setEnabled(false);
		splitPane.setDividerLocation(0.80);
		splitPane.setResizeWeight(1);// 将这个设置修改为1.0会将所有的空间指定给左边或上部的组件
		Action.setJSplitPaneAction(splitPane);
		this.setContentPane(splitPane);
	}

	private void initGraphicControllerPanel ()
	{
		controlJtabbedPanel = new ControlAreaJTabbedPanel(this);
		graph = new GraphShowPanel(this);
	}

	private JTabbedPane initDataShowPanel ()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.add(graph, "测试曲线显示");
		tabbedPane.add(new JPanel(), "光缆地图显示");
		tabbedPane.add(new JPanel(), "告警统计分析");
		tabbedPane.add(new JPanel(), "设置告警定制");
		tabbedPane.add(new JPanel(), "设置告警提示");
		tabbedPane.setTabPlacement(SwingConstants.BOTTOM);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		return tabbedPane;
	}

	private void initMenuBar ()
	{
		checkerMenuBar = new MainMenuBar(this);
		setJMenuBar(checkerMenuBar);
	}

	public void curAttrAction ()
	{
		am = new AboutMessage(this);
		am.showMessage();
	}

	public void saveAction ()
	{
		// saveDialog = new PoPDialog(this);
		SaveWithoughtConfirmPoPDialog.save(this);
		WindowControlEnv.setPortDataHaveSaved(true);
	}

	public MainMenuBar geCheckMenuBar ()
	{
		return checkerMenuBar;
	}

	public GraphShowPanel getGraph ()
	{
		return graph;
	}

	public PoPDialog getSaveDialog ()
	{
		return saveDialog;
	}
	// ******************************************* **********************

	public AboutMessage getAm ()
	{
		return am;
	}

	// ********************************设置通讯端口
	public void setPort (String port)
	{
		whichCommPort = port;
	}

	// 获取通讯端口
	public String getPort ()
	{
		return whichCommPort;
	}

	// ************************ 下面完成数据波形显示功能 ******************************
	// *************************** *******************************

	public void showGraph ()
	{
		GraphControllerPanel controlPanel = controlJtabbedPanel.getGraphControllerpanel();
		CurveSelectionPanel.selectPortDataLine();
		controlPanel.setStateWhenRecvedData();
		geCheckMenuBar().setSaveItemState(true);
		graph.repaint();
	}

	public void showFileGraph ()
	{
		GraphControllerPanel controlPanel = controlJtabbedPanel.getGraphControllerpanel();
		// controlPanel.getCurSelectionPanel().selectFileDataLine();
		CurveSelectionPanel.selectFileDataLine();
		controlPanel.setStateWhenRecvedData();
		geCheckMenuBar().setSaveItemState(true);
		graph.repaint();
	}

	public void setCommunationPort (String port)
	{
		whichCommPort = port;
	}

	// ******************************** *********************
	// ******************************** 整个系统的入口 **********************
	// ******************************* ***********************

	public static void main (String[] args)
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run ()
			{
				createAndShowGUI();
			}
		});

	}

	private static void createAndShowGUI ()
	{
		Md711MainFrame window = new Md711MainFrame();

		window.setTitle(ProductInfo.getProductName());

		// 让屏幕最大化
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		GraphicsConfiguration config = window.getGraphicsConfiguration();
		Insets insets = kit.getScreenInsets(config);
		screenSize.width -= (insets.left + insets.right);
		screenSize.height -= (insets.top + insets.bottom);
		window.setSize(screenSize);
		window.setLocation(insets.left, insets.top);
		window.setVisible(true);
	}

	public GraphControllerPanel getGraphControllerpanel ()
	{
		return controlJtabbedPanel.getGraphControllerpanel();
	}
}
