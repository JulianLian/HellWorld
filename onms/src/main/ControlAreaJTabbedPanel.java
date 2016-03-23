package main;

import java.util.List;
import java.util.Map;

import javax.swing.JTabbedPane;

import datastruct.EventDataStruct;
import dataview.GraphControllerPanel;

public class ControlAreaJTabbedPanel extends JTabbedPane
{
	private Md711MainFrame mainFrame;
//	private GraphControllerPanel graphControllerpanel;
	private KeyPointPanel keyPointPanel;
	private QueryParamPanel queryParamPanel;
	public ControlAreaJTabbedPanel(Md711MainFrame mainFrame)
	{
		super();
		this.mainFrame = mainFrame;
//		initGraphicControllerPanel();
		intKeyPointPanel();
		initQueryParamPanel();
//		this.add(graphControllerpanel, "图像控制");
		this.add(keyPointPanel, "关键事件");
		this.add(queryParamPanel, "图形属性");
	}
	
	private void intKeyPointPanel ()
	{
		keyPointPanel = new KeyPointPanel(mainFrame);
	}
	
	private void initQueryParamPanel ()
	{
		queryParamPanel = new QueryParamPanel();
	}
	
	public void showKeyPoints(List<String> data, Map<String, String> getSelectedDevParam)
	{
		keyPointPanel.showKeyPointsWithOneEventOneString(data, getSelectedDevParam);
	}
	
	public void showEventDataStructWhenReadFromFile(List<EventDataStruct> data,
			Map<String, String> selectedDevParam)
	{
		keyPointPanel.showEventDataStructWhenReadFromFile(data, selectedDevParam);
	}
	
//	private void initGraphicControllerPanel ()
//	{
//		graphControllerpanel = new GraphControllerPanel(mainFrame);
//	}
	
//	public GraphControllerPanel getGraphControllerpanel ()
//	{
//		return graphControllerpanel;
//	}
	
	public void clearKeyPointData()
	{
		keyPointPanel.clearTableData();
	}
	
	public void clearQueryPropertyData()
	{
		queryParamPanel.reset();
	}
	
	public KeyPointPanel getkeyPointPanel()
	{
		return keyPointPanel;
	}

	public void showQueryPropertyPanel (Map<String, String> selectedDevParam)
	{
		queryParamPanel.setQueryproperty(selectedDevParam);
	}
}
