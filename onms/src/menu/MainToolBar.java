package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JToolBar;

import communation.FileDataGetter;
import communation.IDataGetter;
import communation.dialog.CommuParamConfigDialog;
import dataview.Constant;
import dataview.CurveSelectionPanel;
import main.Md711MainFrame;
import persistant.WindowControlEnv;

public class MainToolBar
{
	public static JToolBar createToolBar (Md711MainFrame mainFrame)
	{
		JToolBar toolBar = new JToolBar("CommunicationToolBar");
		addOpenButton(mainFrame, toolBar);		
		addSaveButton(mainFrame, toolBar);
		addQueryButton(mainFrame, toolBar);
	        toolBar.setFloatable(false);
//	        toolBar.setRollover(true);
	        return toolBar;
	}

	private static void addQueryButton (Md711MainFrame mainFrame , JToolBar toolBar)
	{
		JButton queryDevDataButton = new JButton(Constant.createImageIcon("start.png"));
		queryDevDataButton.setToolTipText("获取光纤数据");
		queryDevDataButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed (ActionEvent e)
					{
						CommuParamConfigDialog.showDialog(mainFrame);						
					}			
				});
		toolBar.add(queryDevDataButton);
	}

	private static void addSaveButton (Md711MainFrame mainFrame , JToolBar toolBar)
	{
		JButton saveButton = new JButton( Constant.createImageIcon("save.png"));
		saveButton.setToolTipText("保存波形文件");
		saveButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed (ActionEvent e)
			{
				mainFrame.saveAction();					
			}			
		});
		toolBar.add(saveButton);
	}

	private static void addOpenButton (Md711MainFrame mainFrame , JToolBar toolBar)
	{
		JButton openButton = new JButton(Constant.createImageIcon("open.png"));
		openButton.setToolTipText("载入波形文件");
		openButton.addActionListener(new ActionListener()
				{
					@Override
					public void actionPerformed (ActionEvent e)
					{
						IDataGetter fileDataGetter = new FileDataGetter(mainFrame);
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
					
						}
					}
				});		
		toolBar.add(openButton);
	}
}
