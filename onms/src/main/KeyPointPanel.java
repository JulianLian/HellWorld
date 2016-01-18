package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import datastruct.EventDataStruct;
import dataview.tablemodel.KeyEventTableModel;

public class KeyPointPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel = new KeyEventTableModel();
	private JTable table = new JTable(tableModel);
	private Map<String, Double> eventDataIDPositionMap = new HashMap<String, Double>();
	private List<EventDataStruct> eventData = new ArrayList<EventDataStruct>();
	private Md711MainFrame mainFrame;
	public KeyPointPanel(Md711MainFrame mainFrame)
	{
		layoutPanel();
		this.mainFrame = mainFrame;
	}

	private void layoutPanel ()
	{
		this.setLayout(new GridBagLayout());
		table.setFillsViewportHeight(true);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.getSelectionModel().addListSelectionListener(new RowListener());
		 table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		//坐标变化后的新坐标
		KeyEventTableModel.hideTableColumn(table, table.getColumnCount() - 1);
		JScrollPane scrollPane = new JScrollPane(table);
		this.add(scrollPane,
				new GridBagConstraints(1, 0, 1, 1, 1, 1, 
						GridBagConstraints.CENTER, 
						GridBagConstraints.BOTH,
						new Insets(0, 0, 0, 0), 0, 0));
	}
	
	public void showKeyPointsWithOneEventOneString (List<String> data)
	{
		clearTableData();
		eventDataIDPositionMap.clear();
		eventData.clear();
		for(String oneLineData : data)
		{
			String[] oneLineVal = oneLineData.split(",");
			addOneLineWithLastColMeansEventData(oneLineVal);
			addOneEventData(oneLineVal);
		}		
	}
	
	public void showEventDataStruct (List<EventDataStruct> data)
	{
		clearTableData();
		eventDataIDPositionMap.clear();
		eventData = data;
		for(EventDataStruct oneStruct : data)
		{
			addOneLineWithLastColMeansEventData(
					new String[]{oneStruct.getEventNo(),
					oneStruct.getEventType(), oneStruct.getPosition(),oneStruct.getLoss(),
					oneStruct.getReflect(), oneStruct.getSlop(), oneStruct.getPriviewEventDistance(),
					oneStruct.getOtdr()});
			eventDataIDPositionMap.put(oneStruct.getEventNo(), Double.valueOf(oneStruct.getPosition()));
		}
	}

	private void addOneEventData (String[] oneLineVal)
	{		
		eventDataIDPositionMap.put(oneLineVal[0].trim(), Double.valueOf(oneLineVal[2].trim()));
		eventData.add(new EventDataStruct(oneLineVal[0].trim(), oneLineVal[1].trim(), oneLineVal[2].trim(),
				oneLineVal[3].trim(), oneLineVal[4].trim(), oneLineVal[5].trim(), 
				oneLineVal[6].trim(), oneLineVal[7].trim()));
	}

	private void addOneLineWithLastColMeansEventData (String[] oneLineVal)
	{
		int length = oneLineVal.length + 1;
		Object[] row = new Object[length];
		for(int index = 0 ; index < length - 1; index ++)
		{
			row[index] = oneLineVal[index].trim();
		}		
		//这个字段可以变化，表示横坐标，在放大缩小过程中发生变化
		row[length - 1] = oneLineVal[2].trim(); 
		tableModel.addRow(row);
	}
	
	public void clearTableData()
	{
		table.removeAll();
		tableModel.setRowCount(0);
		eventDataIDPositionMap.clear();
	}
	
	public List<Double> getCashedEventXData ()
	{		
		return new ArrayList<Double>(eventDataIDPositionMap.values());
	}
	
	public List<EventDataStruct>  getEventData()
	{
		return  eventData;
	}
	
	public Map<String, Double> getEventDataIDPositionMap()
	{
		return eventDataIDPositionMap;
	}
	
	 private class RowListener implements ListSelectionListener 
	 {
		 public void valueChanged(ListSelectionEvent event)
		 {
			 if (event.getValueIsAdjusting()) 
			 {
		                return;
			 }
			 int[] selectedRows = table.getSelectedRows();
			 String id = (String)table.getValueAt(selectedRows[0], 0);
			 Double xPosition = eventDataIDPositionMap.get(id.trim());
			 mainFrame.getGraph().showEventVerticalPosition(xPosition);
	}
}

}
