package main;

import dataview.tablemodel.KeyEventTableModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class KeyPointPanel extends JPanel
{
	private DefaultTableModel tableModel = new KeyEventTableModel();
	private JTable table = new JTable(tableModel);
	
	public KeyPointPanel(Md711MainFrame mainFrame)
	{
		layoutPanel();
	}

	private void layoutPanel ()
	{
		this.setLayout(new GridBagLayout());
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane,
				new GridBagConstraints(1, 0, 1, 1, 1, 1, 
						GridBagConstraints.CENTER, 
						GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
	}
	
	public void showKeyPointsWithOneEventOneString (String[] data)
	{
		table.removeAll();
		if(data != null)
		{
			tableModel.addRow(data);	
		}
	}

	public void showKeyPointsWithOneEventOneString (List<String> data)
	{
		table.removeAll();
		for(String oneLineData : data)
		{
			String[] oneLineVal = oneLineData.split(",");
			tableModel.addRow(oneLineVal);	
		}		
	}
	
	public void clearTableData()
	{
		table.removeAll();
	}
}
