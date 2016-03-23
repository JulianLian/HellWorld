package main;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import communation.Protocol;
import dataview.tablemodel.QueryPropertyTableModel;

public class QueryParamPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private DefaultTableModel tableModel = new QueryPropertyTableModel();
	private JTable table = new JTable(tableModel);	
	private Map<String, String> selectedDevParam;
	public QueryParamPanel()
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
	
	public void setQueryproperty (Map<String, String> selectedDevParam)
	{
		table.removeAll();	
		this.selectedDevParam = selectedDevParam;
		
		tableModel.addRow(new Object[]{
				selectedDevParam.get(Protocol.MODULE),
				selectedDevParam.get(Protocol.FUNCTION),
				selectedDevParam.get(Protocol.OTU_IN),
				selectedDevParam.get(Protocol.OTU_OUT),
				selectedDevParam.get(Protocol.ACQUISITION_SETTINT),
				selectedDevParam.get(Protocol.WAVE_LENGTH),
				selectedDevParam.get(Protocol.PULSE_WIDTH),
				selectedDevParam.get(Protocol.RANGE),
				getAcquisitionTimeLabel(selectedDevParam),
				selectedDevParam.get(Protocol.RESOLUTION),
				
				
		});			
	}
	
	private String getAcquisitionTimeLabel(Map<String, String> selectedDevParam)
	{
		String minutes = selectedDevParam.get(Protocol.ACQUISITION_TIME_MINUTES);
		if(minutes != null && !minutes.equals(""))
		{
			minutes += "分";
		}
		else
		{
			minutes = "";
		}
		String seconds = selectedDevParam.get(Protocol.ACQUISITION_TIME_SECONDS);
		if(seconds != null && !seconds.equals(""))
		{
			seconds += "秒";
		}
		else
		{
			seconds = "";
		}
		return minutes+seconds;
	}
	
	public void reset()
	{
		table.removeAll();	
		tableModel.setRowCount(0);
		if(selectedDevParam != null)
		{
			selectedDevParam.clear();
		}
	}
}
