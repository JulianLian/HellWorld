package dataview;

import domain.HardWare;
import main.Md711MainFrame;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;

public class GraphControllerPanel extends JPanel // JTabbedPane
{
	private Md711MainFrame mainFrame;
	
	// ----------------------------------------------------------------------
	private MoveAndAmplifyControllerPanel moveAndAmplyPanel;
	// �����ǽ���ѡ��ť
	// private MediaSelectionPanel mediaSelectionPanel;
	private CurveSelectionPanel curSelectionPanel;
	private FaultDistancePanel faultDistancePanel;
	
	public GraphControllerPanel(Md711MainFrame mainFrame)
	{
		this.mainFrame = mainFrame;
		layoutPanel();
	}
	
	private void layoutPanel ()
	{
		moveAndAmplyPanel = new MoveAndAmplifyControllerPanel(this);
		// **************************************************************************************�йؽ���ѡ��ѡ��·�����еĵ�3�����
		// mediaSelectionPanel = new MediaSelectionPanel(this);
		// mediaSelectionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
		// "����ѡ��",
		// TitledBorder.CENTER, TitledBorder.TOP));
		
		// ***************************************************************************************�·�����еĵ�4�����
		// ************************�йع��Ͼ������
		faultDistancePanel = new FaultDistancePanel();
		// ****************************�й�ѡ�������߶�ѡ��
		curSelectionPanel = new CurveSelectionPanel(this);
		
		JPanel faultAndCurSelectionPanel = new JPanel(new GridLayout(2, 1, 0, 0));
		faultAndCurSelectionPanel.add(faultDistancePanel);
		faultAndCurSelectionPanel.add(curSelectionPanel);
		
		JPanel faultDistanceAndMediaSelectionPanel = new JPanel(new GridLayout(1, 2, 10, 10));
		faultDistanceAndMediaSelectionPanel.add(faultAndCurSelectionPanel);
		// faultDistanceAndMediaSelectionPanel.add(mediaSelectionPanel);
		
		this.setLayout(new GridLayout(1, 2, 10, 10));
		this.add(moveAndAmplyPanel);
		this.add(faultDistanceAndMediaSelectionPanel);
		this.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
	}
	
	public MoveAndAmplifyControllerPanel getMoveAndAmplyPanel ()
	{
		return moveAndAmplyPanel;
	}
	
	public void setStateWhenOpenFile ()
	{
		moveAndAmplyPanel.setStateWhenOpenFile();
		// mediaSelectionPanel.setCanEnable(false);
		curSelectionPanel.setStateEnable(CurveSelectionPanel.FILE_CUR_SELECTION, true);
	}
	
	public void clearAll ()
	{
		moveAndAmplyPanel.initWidgetState();
		// mediaSelectionPanel.setDefaultSelection();
		// mediaSelectionPanel.setCanEnable(false);
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
	
	// public void setMediaSeletionEnState(boolean isEnable)
	// {
	// mediaSelectionPanel.setCanEnable(isEnable);
	// }
	
	public void fillDistanceInfo (String distanceStr)
	{
		faultDistancePanel.fillDistanceInfo(distanceStr);
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
		// setMediaSeletionEnState(true);
	}
	
	public int getCurSelectedCurve ()
	{
		return curSelectionPanel.selectPortOrFileData();
	}
	
	public CurveSelectionPanel getCurSelectionPanel ()
	{
		return curSelectionPanel;
	}
}