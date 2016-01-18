package main;

import java.util.List;

import javax.swing.JTabbedPane;

import datastruct.EventDataStruct;
import dataview.GraphControllerPanel;

public class ControlAreaJTabbedPanel extends JTabbedPane
{
	private Md711MainFrame mainFrame;
	private GraphControllerPanel graphControllerpanel;
	private KeyPointPanel keyPointPanel;

	public ControlAreaJTabbedPanel(Md711MainFrame mainFrame)
	{
		super();
		this.mainFrame = mainFrame;
		initGraphicControllerPanel();
		intKeyPointPanel();
		this.add(graphControllerpanel, "图像控制");
		this.add(keyPointPanel, "关键事件");
	}

	private void intKeyPointPanel ()
	{
		keyPointPanel = new KeyPointPanel(mainFrame);
	}
	
	public void showKeyPoints(List<String> data)
	{
		keyPointPanel.showKeyPointsWithOneEventOneString(data);
	}
	
	public void showEventDataStruct(List<EventDataStruct> data)
	{
		keyPointPanel.showEventDataStruct(data);
	}
	
	private void initGraphicControllerPanel ()
	{
		graphControllerpanel = new GraphControllerPanel(mainFrame);
	}

	public GraphControllerPanel getGraphControllerpanel ()
	{
		return graphControllerpanel;
	}
	
	public void clearKeyPointData()
	{
		keyPointPanel.clearTableData();
	}
	
	public KeyPointPanel getkeyPointPanel()
	{
		return keyPointPanel;
	}
}
