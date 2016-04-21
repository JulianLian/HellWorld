package dataview;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

import domain.BusinessConst;
import env.MDLogger;
import persistant.FileDataPersister;
import persistant.IDataPersister;
import persistant.PortDataPersister;
import persistant.WindowControlEnv;

public class MoveAndAmplifyControllerPanel extends JPanel implements ActionListener
{
	private GraphControllerPanel controlPanel;
//	private FaultDistancePanel distancepanel;
	
	private JProgressBar progressBar = new JProgressBar(0, 4096);
	private JButton restoreButton = new JButton("复原",  Constant.createImageIcon("restore.png"));

	private JButton jbStep;// 步长

	private JTextField jtfStep;

	private JButton jbAug; // 放大
	private JTextField jtfAug;

	private JButton jbShrink; // 缩小
	private JTextField jtfShrink;

	private JButton jbUpMove; // 上移动
	private JTextField jtfUpMove;

	private JButton jbDownMove; // 下移动
	private JTextField jtfDownMove;

	private JButton jbLeftMove; // 左移
	private JTextField jtfLeftMove;

	private JButton jbRightMove; // 右移动
	private JTextField jtfRightMove;

	private JButton jbWinzip; // 压缩图形
	private JTextField jtfWinzip;

	private JButton jbAntiWinzip; // 拉伸图形
	private JTextField jtfAntiWinzip;

	public MoveAndAmplifyControllerPanel(GraphControllerPanel controlPanel, LayoutManager mgr)
	{
		super();
		this.controlPanel = controlPanel;
		setLayout(mgr == null ? new GridLayout(4, 1) : mgr);		
		layoutPanel();
	}

	public MoveAndAmplifyControllerPanel(GraphControllerPanel controlPanel)
	{
		this(controlPanel, new GridLayout(4,1));
	}
	
	private void layoutPanel ()
	{
		JPanel downFirstPane = controlArea1();		
		JPanel downSecondPane = controlArea2();
		layoutMeasureDistancePanel();
//		setAllEnState(false);
		setAllEnState(true);
		this.add(downFirstPane);
//		this.add(downSecondPane);//不要上下左右移动
		this.add(controlPanel);
//		this.add(distancepanel);
	}

	private JPanel controlArea2 ()
	{
		JPanel downSecondPane = new JPanel(new GridLayout(5, 2));

		jbUpMove = new JButton("上移",  Constant.createImageIcon("up.gif"));
		setMoveControlWidgetSize(jbUpMove);
		jtfUpMove = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfUpMove);
		jbUpMove.setToolTipText("图形上移");
		jtfUpMove.setToolTipText("图形上移象素");
		jbUpMove.addActionListener(this);

		ImageIcon downIcon =  Constant.createImageIcon("down.gif");
		jbDownMove = new JButton("下移", downIcon);
		setMoveControlWidgetSize(jbDownMove);
		jtfDownMove = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfDownMove);
		jbDownMove.setToolTipText("图形下移");
		jtfDownMove.setToolTipText("图形下移象素");
		jbDownMove.addActionListener(this);

		ImageIcon leftIcon =  Constant.createImageIcon("left.gif");
		jbLeftMove = new JButton("左移", leftIcon);
		setMoveControlWidgetSize(jbLeftMove);
		jtfLeftMove = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfLeftMove);
		jbLeftMove.setToolTipText("图形左移");
		jtfLeftMove.setToolTipText("图形左移象素");
		jbLeftMove.addActionListener(this);

		ImageIcon rightIcon =  Constant.createImageIcon("right.gif");
		jbRightMove = new JButton("右移", rightIcon);
		setMoveControlWidgetSize(jbRightMove);
		jtfRightMove = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfRightMove);
		jbRightMove.setToolTipText("图形右移");
		jtfRightMove.setToolTipText("图形右移象素");
		jbRightMove.addActionListener(this);
		// ************************下方面板中的第1块结束		
		progressBar.setValue(0);		
		// ************************下方面板中的第2块
		setMoveControlWidgetSize(restoreButton);
		restoreButton.addActionListener(this);
		
		// JButton jb = new JButton("通讯进度");
		// jb.setToolTipText("端口通讯进度，若有,后面则后面进度条显示进度");
		// jb.setEnabled(false);
		// downSecondPane.add(jb);
		// downSecondPane.add(progressBar);
		
//		downSecondPane.add(new JLabel());
//		downSecondPane.add(restoreButton);		
		
		
		
		
//		downSecondPane.add(jtfUpMove);
//		downSecondPane.add(jbUpMove);		
//		
//		downSecondPane.add(jtfDownMove);
//		downSecondPane.add(jbDownMove);
		
		downSecondPane.add(jtfLeftMove);
		downSecondPane.add(jbLeftMove);
		
		downSecondPane.add(jtfRightMove);
		downSecondPane.add(jbRightMove);
		
		
//		downSecondPane.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
		return downSecondPane;
	}
	
	private void layoutMeasureDistancePanel()
	{
//		distancepanel = new  FaultDistancePanel();
//		controlPanel
	}
	
	private void setMoveControlWidgetSize(Component comp)
	{
		comp.setPreferredSize(new Dimension(85,10));
	}
	
	private void setMoveControlFieldWidgetSize(Component comp)
	{
		comp.setPreferredSize(new Dimension(20,10));
	}

//	public FaultDistancePanel getDistancepanel ()
//	{
//		return distancepanel;
//	}
	
	public GraphControllerPanel getGraphicControlPanel ()
	{ 
		return controlPanel;
	}

	private JPanel controlArea1 ()
	{
		JPanel downFirstPane = new JPanel();
		downFirstPane.setLayout(new GridLayout(0, 2));
		
		jbStep = new JButton("设定步长");
		setMoveControlWidgetSize(jbStep);
		jtfStep = new JTextField("1");
		setMoveControlFieldWidgetSize(jtfStep);
		jtfStep.setToolTipText("采样步长");
		jtfStep.setToolTipText("相隔几个点采样");
		jbStep.addActionListener(this);
		setStepEnable(false);
		
		jbAug = new JButton("放大" , Constant.createImageIcon("zoomIn.gif"));
		setMoveControlWidgetSize(jbAug);
		jtfAug = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfAug);
		jbAug.setToolTipText("图形放大");
		jtfAug.setToolTipText("图形放大倍数");
		jbAug.addActionListener(this);
		
		jbShrink = new JButton("缩小" ,  Constant.createImageIcon("zoomOut.gif"));
		setMoveControlWidgetSize(jbShrink);
		jtfShrink = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfShrink);
		jbShrink.setToolTipText("图形缩小");
		jtfShrink.setToolTipText("图形缩小倍数");
		jbShrink.addActionListener(this);	

		jbWinzip = new JButton("压缩",  Constant.createImageIcon("winzip.gif"));
		setMoveControlWidgetSize(jbWinzip);
		jtfWinzip = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfWinzip);
		jbWinzip.setToolTipText("图形横向压缩");
		jtfWinzip.setToolTipText("图形横向压缩倍数");
		jbWinzip.addActionListener(this);

		jbAntiWinzip = new JButton("拉伸",Constant.createImageIcon("arrows_stretch.png"));
		setMoveControlWidgetSize(jbAntiWinzip);
		jtfAntiWinzip = new JTextField("2");
		setMoveControlFieldWidgetSize(jtfAntiWinzip);
		jbAntiWinzip.setToolTipText("图形横向拉伸");
		jtfAntiWinzip.setToolTipText("图形横向拉伸倍数");
		jbAntiWinzip.addActionListener(this);


		downFirstPane.add(new JLabel());
		downFirstPane.add(restoreButton);	
		
//设定步长功能不提供了，屏蔽掉
//		downFirstPane.add(jtfStep);
//		downFirstPane.add(jbStep);
		
		downFirstPane.add(jtfAug);
		downFirstPane.add(jbAug);
		
		downFirstPane.add(jtfShrink);
		downFirstPane.add(jbShrink);
		
		downFirstPane.add(jtfWinzip);
		downFirstPane.add(jbWinzip);
		
		downFirstPane.add(jtfAntiWinzip);
		downFirstPane.add(jbAntiWinzip);
//		downFirstPane.setBorder(BorderFactory.createEmptyBorder(7, 5, 5, 5));
		return downFirstPane;
	}

	public JButton getJbStep ()
	{
		return jbStep;
	}

	public JTextField getJtfStep ()
	{
		return jtfStep;
	}

	@Override
	public void actionPerformed (ActionEvent e)
	{
		if (e.getSource().equals(jbStep))
		{
			jbStepAction();
		}
		// *********************************************点击放大按钮
		if (e.getSource().equals(jbAug) && this.getAug() != 0)
		{

			// this.jtfShrink.setText("1");//如果放大，则让缩小文本框中大小自动设置为1

			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				amplyOrShrink(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						true);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				amplyOrShrink(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), true);
			}
			mainFrameRepaint();
		}

		// ******************************************
		if (e.getSource().equals(jbShrink) && this.getShrink() != 0)
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				amplyOrShrink(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()),
						false);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				amplyOrShrink(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), false);
			}
			mainFrameRepaint();
		}

		// ***********************************
		if (e.getSource().equals(jbUpMove))
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				moveVertical(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						true);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				moveVertical(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), true);
			}

			mainFrameRepaint();
		}

		// ********************************
		if (e.getSource().equals(jbDownMove))
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				moveVertical(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						false);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				moveVertical(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), false);
			}
			mainFrameRepaint();
		}

		// **********************************
		if (e.getSource().equals(jbLeftMove))
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				moveHorizontal(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						true);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				moveHorizontal(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), true);
			}

			mainFrameRepaint();
		}

		// ***********************************
		if (e.getSource().equals(jbRightMove))
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				moveHorizontal(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						false);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				moveHorizontal(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), false);
			}
			mainFrameRepaint();
		}

		// **********************************
		if (e.getSource().equals(jbWinzip) && this.getWinzip() != 0)
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				horizontalWinZipOrAnti(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), 
						true);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				horizontalWinZipOrAnti(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), true);
			}
			mainFrameRepaint();
		}

		// *************************************
		// 注意这里的获取异常在get函数钟已经捕获
		if (e.getSource().equals(jbAntiWinzip) && this.getAntiWinzip() != 0)
		{
			if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				horizontalWinZipOrAnti(PortDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), false);
			}
			else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				horizontalWinZipOrAnti(FileDataPersister.getInstance(
						controlPanel.getMainFrame().getEventPanel().getkeyPointPanel()), false);
			}
			mainFrameRepaint();
		}

		if (e.getSource().equals(restoreButton))
		{
			initWidgetState();
			setAllEnState(true);
			WindowControlEnv.setRepaintForPortInfoCome(true);
			WindowControlEnv.setRepaintForFileInfoCome(true);
			mainFrameRepaint();
		}
	}

	private void jbStepAction ()
	{
		if (controlPanel.getCurSelectedCurve() == BusinessConst.PORTSELECT)
		{
			WindowControlEnv.setPortStepNotClicked(false);// "用户已经点击步长按钮"
			WindowControlEnv.setRepaintForPortInfoCome(true);// 重新计算坐标，如同刚接收数据一样
			WindowControlEnv.setStepValForPortData(this.getStep());// 把步长设置好
		}
		else if (controlPanel.getCurSelectedCurve() == BusinessConst.FILESELECT)
		{
			WindowControlEnv.setFileStepNotClicked(false);
			WindowControlEnv.setRepaintForFileInfoCome(true);
			WindowControlEnv.setStepValForFileData(this.getStep());
		}
		setStepEnable(false);
		jbStep.setBackground(new Color(238, 238, 238));
		
		setAllEnState(true);
		mainFrameRepaint();
	}
	
	public void amplyOrShrink (IDataPersister dataPersister , boolean isAmplify)
	{
		double amplifyVal = isAmplify ? this.getAug() : 1. / this.getShrink();
		amplyOrShrink(dataPersister, amplifyVal);
	}

	public static void amplyOrShrink (IDataPersister dataPersister , double amplifyFactors)
	{
		List<Double> yData = dataPersister.getCashedYData();
		for (int i = 0, length = yData.size(); i < length; i++)
			yData.set(i, yData.get(i).doubleValue() * amplifyFactors);

		List<Double> xData = dataPersister.getCashedXData();
		for (int j = 0, length = xData.size(); j < length; j++)
			xData.set(j, xData.get(j).doubleValue() * amplifyFactors);
	}

	public void moveVertical (IDataPersister dataPersister , boolean isUpDirection)
	{
		double delta = isUpDirection ? (-1) * this.getUpMove() : this.getDownMove();
		moveVertical(dataPersister, delta);
	}

	public static void moveVertical (IDataPersister dataPersister , double delta)
	{
		List<Double> yData = dataPersister.getCashedYData();
		for (int i = 0, length = yData.size(); i < length; i++)
		{
			yData.set(i, yData.get(i).doubleValue() + delta);
		}
	}

	private void moveHorizontal (IDataPersister dataPersister , boolean isLeft)
	{
		double delta = isLeft ? (-1) * this.getLeftMove() : this.getRightMove();
		moveHorizontal(dataPersister, delta);
	}

	public static void moveHorizontal (IDataPersister dataPersister , double delta)
	{
		List<Double> xData = dataPersister.getCashedXData();
		for (int j = 0, length = xData.size(); j < length; j++)
		{
			xData.set(j, xData.get(j).doubleValue() + delta);
		}
	}

	private void horizontalWinZipOrAnti (IDataPersister dataPersister , boolean isWinZip)
	{
		List<Double> xData = dataPersister.getCashedXData();
		double factor = isWinZip ? 1. / this.getWinzip() : this.getAntiWinzip();

		for (int j = 0, length = xData.size(); j < length; j++)
		{
			xData.set(j, xData.get(j).doubleValue() * factor);
		}
	}

	public void mainFrameRepaint ()
	{
		controlPanel.getMainFrame().getGraph().repaint();
	}

	// *************************************使用者在界面文本框中输入的内容
	public int getStep ()
	{
		try
		{
			String temp = jtfStep.getText();
			if (temp.equals("0"))
			{
				jtfStep.setText("1");
				return 1;
			}
			else
				return Integer.parseInt(temp);
		}
		catch (Exception e)
		{
		        MDLogger.INS.error(e.getMessage());
			// 这里就有用户写小数时的处理方法
			jtfStep.setText("1");
			return 1;
		}

	}

	public double getAug ()
	{
		try
		{
			String temp = jtfAug.getText();
			if (temp.equals("0"))
			{
				jtfAug.setText("2");
				return 2;
			}
			else
				return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfAug.setText("2");
			return 2;
		}

	}

	public void setAug (double times)
	{
		jtfAug.setText(String.format("%.2f ", times));
	}

	public double getShrink ()
	{
		try
		{

			String temp = jtfShrink.getText();
			if (temp.equals("0"))
			{
				jtfShrink.setText("2");
				return 2;
			}
			else
				return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfShrink.setText("2");
			return 2;
		}

	}

	public double getUpMove ()
	{
		try
		{

			String temp = jtfUpMove.getText();
			return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfUpMove.setText("2");
			return 2;
		}

	}

	public void setUpMove (double val)
	{
		jtfUpMove.setText(String.format("%.2f ", val));
	}

	public double getDownMove ()
	{
		try
		{
			String temp = jtfDownMove.getText();
			return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfDownMove.setText("2");
			return 2;
		}
	}

	public void setDownMove (double val)
	{
		jtfDownMove.setText(String.format("%.2f ", val));
	}

	public double getLeftMove ()
	{
		try
		{

			String temp = jtfLeftMove.getText();
			return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfLeftMove.setText("2");
			return 2;
		}

	}

	public void setLeftMove (double val)
	{
		jtfLeftMove.setText(String.format("%.2f ", val));
	}

	public double getRightMove ()
	{
		try
		{

			String temp = jtfRightMove.getText();
			return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfRightMove.setText("2");
			return 2;
		}

	}

	public void setRightMove (double val)
	{
		jtfRightMove.setText(String.format("%.2f ", val));
	}

	public double getWinzip ()
	{
		try
		{
			String temp = jtfWinzip.getText();
			if (temp.equals("0"))
			{

				jtfWinzip.setText("2");
				return 2;
			}
			else
				return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfWinzip.setText("2");
			return 2;
		}

	}

	public double getAntiWinzip ()
	{
		try
		{
			String temp = jtfAntiWinzip.getText();
			if (temp.equals("0"))
			{
				jtfAntiWinzip.setText("2");
				return 2;
			}
			else
				return Double.parseDouble(temp);
		}
		catch (Exception e)
		{
			MDLogger.INS.error(e.getMessage());
			jtfAntiWinzip.setText("2");
			return 2;
		}

	}

	public void setProgress (int howByteReceived)
	{
		progressBar.setValue(howByteReceived);
	}

	public void setStateWhenRecvedData ()
	{
		setStepEnable(true);
		jbStep.setBackground(Color.red);
	}

	public void jbGreenSelectAction ()
	{
		if (WindowControlEnv.getFileStepNotClicked() == false)
		{
//			setStepEnable(false);
//			setAllEnState(true);

			// controlPanel.setMediaSeletionEnState(true);
			jbStep.setBackground(new Color(238, 238, 238));

		}

		else
		{
//			setStepEnable(true);
//			setAllEnState(false);

			// controlPanel.setMediaSeletionEnState(false);

			jbStep.setBackground(Color.RED);

		}
	}

	public void jbRedSelectAction ()
	{
		if (WindowControlEnv.getPortStepNotClicked() == false)
		{
//			setStepEnable(false);
//			setAllEnState(true);
			// controlPanel.setMediaSeletionEnState(true);

			jbStep.setBackground(new Color(238, 238, 238));

		}
		else
		{
//			setStepEnable(true);
//			setAllEnState(false);
			// controlPanel.setMediaSeletionEnState(false);

			jbStep.setBackground(Color.RED);

		}
	}

	public void initWidgetState ()
	{
		setStepEnable(false);
//		setAllEnState(false);
		setAllEnState(true);

		jtfStep.setText("1");
		jtfAug.setText("2");
		jtfShrink.setText("2");
		jtfUpMove.setText("2");
		jtfDownMove.setText("2");
		jtfLeftMove.setText("2");
		jtfRightMove.setText("2");
		jtfWinzip.setText("2");
		jtfAntiWinzip.setText("2");
		jbStep.setBackground(new Color(238, 238, 238));

		progressBar.setValue(0);
	}

	public void setStateWhenOpenFile ()
	{
//		setAllEnState(false);
		setAllEnState(true);
		setStepEnable(true);
		jbStep.setBackground(Color.red);
	}

	public void setAllEnState (boolean isEn)
	{
		jbAug.setEnabled(isEn);
		jtfAug.setEnabled(isEn);
		jbShrink.setEnabled(isEn);
		jtfShrink.setEnabled(isEn);
		jbUpMove.setEnabled(isEn);
		jtfUpMove.setEnabled(isEn);
		jbDownMove.setEnabled(isEn);
		jtfDownMove.setEnabled(isEn);
		jbLeftMove.setEnabled(isEn);
		jtfLeftMove.setEnabled(isEn);
		jbRightMove.setEnabled(isEn);
		jtfRightMove.setEnabled(isEn);
		jbWinzip.setEnabled(isEn);
		jtfWinzip.setEnabled(isEn);
		jbAntiWinzip.setEnabled(isEn);
		jtfAntiWinzip.setEnabled(isEn);
		restoreButton.setEnabled(isEn);
	}

	public void setStepEnable (boolean isEnable)
	{
		jbStep.setEnabled(isEnable);
		jtfStep.setEnabled(isEnable);
	}

	// **********************这是用来创建一个图标对象的，我们用来放在按钮上做图像
//	protected static ImageIcon createImageIcon (String path)
//	{
//		java.net.URL imgURL = MoveAndAmplifyControllerPanel.class.getResource(dataview.Constant.BASE_IMG_PATH + path);
//		if (imgURL != null)
//		{
//			return new ImageIcon(imgURL);
//		}
//		else
//		{
//			System.err.println("Couldn't find file: " + path);
//			return null;
//		}
//	}
}
