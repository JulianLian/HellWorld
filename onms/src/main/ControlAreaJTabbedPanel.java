package main;

import dataview.GraphControllerPanel;

import javax.swing.*;

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
		keyPointPanel = new KeyPointPanel();
	}

	private void initGraphicControllerPanel ()
	{
		graphControllerpanel = new GraphControllerPanel(mainFrame);
	}

	public GraphControllerPanel getGraphControllerpanel ()
	{
		return graphControllerpanel;
	}
}
