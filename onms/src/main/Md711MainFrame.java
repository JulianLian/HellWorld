
package main;
/*
 * 作者：         杨安印
 * 开始日期：     2005-8-8
 * 预期完成时间： 40天
 */

import action.Action;
import communation.FileDataGetter;
import dataview.CurveSelectionPanel;
import dataview.GraphControllerPanel;
import dataview.GraphShowPanel;
import dataview.MoveAndAmplifyControllerPanel;
import dir.*;
import domain.HardWare;
import menu.MainMenuBar;
import menu.MainToolBar;
import persistant.PoPDialog;
import persistant.SaveWithoughtConfirmPoPDialog;
import persistant.WindowControlEnv;
import rule.OnlyFixedPointRuleVIew;
import rule.RuleView;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.io.File;
import java.util.List;

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
	private MoveAndAmplifyControllerPanel moveAndAmplyPanel;
	
	public Md711MainFrame()
	{
		Action.closingAction(this);
		initMenuBar();
		initGraphicControllerPanel();
		layoutPanel();		
	}
	
	private void layoutPanel ()
	{
		setLayout(new BorderLayout());
		JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, true);
		JComponent graphScrollPane = initDataShowPanelWithFileDir();
		splitPane.setLeftComponent(graphScrollPane);
		
		JScrollPane controlPanel = new JScrollPane(controlJtabbedPanel);
//		controlPanel.setMaximumSize(new Dimension(controlPanel.getPreferredSize().width, 100));
		controlPanel.setPreferredSize(new Dimension(controlPanel.getPreferredSize().width, 100));
		controlPanel.setMinimumSize(new Dimension(controlPanel.getPreferredSize().width, 100));
		splitPane.setRightComponent(controlPanel);
		graphScrollPane.setMinimumSize(new Dimension(800, 560));
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
//		splitPane.setEnabled(false);
		splitPane.setDividerLocation(0.80);
		splitPane.setResizeWeight(0.5);// 将这个设置修改为1.0会将所有的空间指定给左边或上部的组件
//		Action.setJSplitPaneAction(splitPane);
		
		add(MainToolBar.createToolBar(this), BorderLayout.PAGE_START);
		add(splitPane, BorderLayout.CENTER);
	}
	
	private void initGraphicControllerPanel ()
	{
		controlJtabbedPanel = new ControlAreaJTabbedPanel(this);
		graph = new GraphShowPanel(this);
	}
	
	private JComponent initDataShowPanel ()
	{
		JTabbedPane tabbedPane = new JTabbedPane();
		layoutGraphicShowPanel(tabbedPane);
		tabbedPane.add(new JPanel(), "光缆地图显示");
		tabbedPane.add(new JPanel(), "告警统计分析");
		tabbedPane.add(new JPanel(), "设置告警定制");
		tabbedPane.add(new JPanel(), "设置告警提示");
//		tabbedPane.setTabPlacement(SwingConstants.BOTTOM);
		tabbedPane.setTabPlacement(SwingConstants.NORTH);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		
		return tabbedPane;
	}
	
	private void layoutGraphicShowPanel (JTabbedPane tabbedPane)
	{
		moveAndAmplyPanel = new  MoveAndAmplifyControllerPanel(
				new GraphControllerPanel(this),
				new GridLayout(4,1));
		JPanel panel = new JPanel(new BorderLayout());
		JComponent ruleGraphPanel = formGraphicPanelWithRule();
		panel.add(ruleGraphPanel, BorderLayout.CENTER);
//		panel.add(graph, BorderLayout.CENTER);
		panel.add(moveAndAmplyPanel, BorderLayout.EAST);			
//		tabbedPane.add(graph, "测试曲线显示");
		tabbedPane.add(panel, "测试曲线显示");
	}
	
	private JComponent formGraphicPanelWithRule ()
	{
		JScrollPane panel = new JScrollPane();			
		OnlyFixedPointRuleVIew horizontalRuler = new OnlyFixedPointRuleVIew(RuleView.HORIZONTAL);   
		horizontalRuler.setPreferredHeight(30); 
		horizontalRuler.setMaxRuleLabelLen(30);
		panel.setColumnHeaderView(horizontalRuler); 
		
		OnlyFixedPointRuleVIew verticalRuler = new OnlyFixedPointRuleVIew(RuleView.VERTICAL);  
		verticalRuler.setPreferredWidth(40);		
		panel.setRowHeaderView(verticalRuler);     
		
		panel.setViewportView(graph);		
//		JLabel cornerLabel = new JLabel(""); 
//		cornerLabel.setForeground(OnlyFixedPointRuleVIew.RULE_BG_COLOR);
//		panel.setCorner(JScrollPane.UPPER_LEFT_CORNER, cornerLabel); 
		
		graph.setVHRulerAndTheirContainer(verticalRuler, horizontalRuler, panel);
		return panel;
	}


	private JComponent initDataShowPanelWithFileDir()
	{
		JComponent dataShowPanel = initDataShowPanel ();
		FileTree fileTree = new FileTree();
		FileTreeModel model = new FileTreeModel(
				new DefaultMutableTreeNode(new FileNode("root", null, null, true)));
		fileTree.setModel(model);
		fileTree.setCellRenderer(new FileTreeRenderer());
		fileTree.addTreeMouseClickAction(new FileTreeMouseAdapter(fileTree));
		fileTree.addTreeSelectionListener(new TreeSelectionListener()
		{  
			@Override  
			public void valueChanged(TreeSelectionEvent e) 
			{  
			       DefaultMutableTreeNode selectedNode=
					       (DefaultMutableTreeNode) fileTree.getLastSelectedPathComponent();//返回最后选定的节点  
			       if(selectedNode == null)
			       {
				       return;
			       }
			       File file =( (FileNode)selectedNode.getUserObject()).file;
			       if(file.isDirectory())
			       {
				       return;
			       }
			       List<Double> waveData = FileDataGetter.readFileData(file, Md711MainFrame.this);
			       showWaveData(waveData);
			   }  
			});		
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
		splitPane.setLeftComponent(new JScrollPane(fileTree));
		splitPane.setRightComponent(dataShowPanel);
		splitPane.setOneTouchExpandable(true);
		splitPane.setContinuousLayout(true);
		splitPane.setDividerLocation(0.20);
		splitPane.setResizeWeight(0.1);
		Action.setJSplitPaneAction(splitPane);
		return splitPane;
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
//		GraphControllerPanel controlPanel = controlJtabbedPanel.getGraphControllerpanel();
		CurveSelectionPanel.selectPortDataLine();
		getMoveAndAmplyPanel().setStateWhenRecvedData();
		geCheckMenuBar().setSaveItemState(true);
		graph.repaint();
	}
	
	public void showFileGraph ()
	{
//		GraphControllerPanel controlPanel = controlJtabbedPanel.getGraphControllerpanel();
		// controlPanel.getCurSelectionPanel().selectFileDataLine();
		CurveSelectionPanel.selectFileDataLine();
		getMoveAndAmplyPanel().setStateWhenRecvedData();
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
		
//		window.setTitle(I18n.INS.getI18Str("frameTitle"));
		window.setTitle("xxx光线检测系统");

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
	
	public  void showWaveData (List<Double> waveData)
	{
		if (waveData != null)
		{
			// mainFrame.getGraphControllerpanel().getCurSelectionPanel().selectFileDataLine();
			CurveSelectionPanel.selectFileDataLine();
			WindowControlEnv.setRepaintForFileInfoCome(true);
			setStateWhenOpenFile();
			this.showFileGraph();
			this.getGraphControllerpanel().getCurSelectionPanel()
					.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, true);			
		}
		else
		{
			moveAndAmplyPanel.setStepEnable(false);
//			this.getGraphControllerpanel().getMoveAndAmplyPanel().setStepEnable(false);
		}
	}
	
	public void setStateWhenOpenFile ()
	{
		moveAndAmplyPanel.setStateWhenOpenFile();	
		this.getGraphControllerpanel().setStateWhenOpenFile();
	}
	
	public GraphControllerPanel getGraphControllerpanel ()
	{
//		return controlJtabbedPanel.getGraphControllerpanel();
		return moveAndAmplyPanel.getGraphicControlPanel();
	}
	
	public ControlAreaJTabbedPanel getEventPanel ()
	{
		return controlJtabbedPanel;
	}

	public MoveAndAmplifyControllerPanel getMoveAndAmplyPanel ()
	{
		return moveAndAmplyPanel;
	}

	public void clearControlParam ()
	{
		moveAndAmplyPanel.initWidgetState();
		getGraphControllerpanel().clearAll();		
	}
	
	public void jbRedSelectAction ()
	{
		moveAndAmplyPanel.jbRedSelectAction();
	}
	
	public void jbGreenSelectAction ()
	{
		moveAndAmplyPanel.jbGreenSelectAction();
	}
	
	public void setProgress (int howByteReceived)
	{
		moveAndAmplyPanel.setProgress(howByteReceived);
}

	public void setStateWhenRecvedData ()
	{
		moveAndAmplyPanel.setStateWhenRecvedData();		
	}
}
