package dataview.tablemodel;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class QueryPropertyTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = { "Module", "Function", "Switch In", "Switch Out", "查询方式", "波长", "脉冲宽度",
			"范围", "获取时间", "分辨率" };
			
	private static Vector<String> columnNameList = new Vector<String>(Arrays.asList(columnNames));
	
	@Override
	public boolean isCellEditable (int row , int column)
	{
		return false;
	}
	
	public QueryPropertyTableModel()
	{
		super(columnNameList, 0);
	}
}

