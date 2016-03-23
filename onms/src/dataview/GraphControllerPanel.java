package dataview;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import domain.HardWare;
import main.Md711MainFrame;

public class GraphControllerPanel extends JPanel // JTabbedPane
{
	private Md711MainFrame mainFrame;
	
	// ----------------------------------------------------------------------
//	private MoveAndAmplifyControllerPanel moveAndAmplyPanel;	
	private CurveSelectionPanel curSelectionPanel;
	private FaultDistancePanel faultDistancePanel;
	
	public GraphControllerPanel(Md711MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		layoutPanel();
	}
	
	private void layoutPanel ()
	{
//		moveAndAmplyPanel = new MoveAndAmplifyControllerPanel(this);
		
		// ***************************************************************************************下方面板中的第4个面板
		// ************************有关故障距离面板
		faultDistancePanel = new FaultDistancePanel();
		// ****************************有关选择哪条线段选项
		curSelectionPanel = new CurveSelectionPanel(this);
		
		JPanel faultAndCurSelectionPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		faultAndCurSelectionPanel.add(faultDistancePanel);
		faultAndCurSelectionPanel.add(curSelectionPanel);
		
		JPanel faultDistanceAndMediaSelectionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		faultDistanceAndMediaSelectionPanel.add(faultAndCurSelectionPanel);
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
//		this.add(moveAndAmplyPanel);
		this.add(faultDistanceAndMediaSelectionPanel);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
	
	public void setStateWhenOpenFile ()
	{
		curSelectionPanel.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, true);
	}
	
	public void clearAll ()
	{
		curSelectionPanel.setStateEnable(CurveSelectionPanel.PORT_CUR_SELECTION, false);
		curSelectionPanel.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, false);
		curSelectionPanel.setSelectNothing();
		mainFrame.setCommunationPort(HardWare.COM1);
		faultDistancePanel.clearDistance();
	}
	
	public Md711MainFrame getMainFrame ()
	{
		return mainFrame;
	}
	
	
	public void fillDistanceInfo (String distanceStr)
	{
		faultDistancePanel.fillDistanceInfo(distanceStr);
	}
	
	public int getCurSelectedCurve ()
	{
		return CurveSelectionPanel.selectPortOrFileData();
	}
	
	public CurveSelectionPanel getCurSelectionPanel ()
	{
		return curSelectionPanel;
	}
}
