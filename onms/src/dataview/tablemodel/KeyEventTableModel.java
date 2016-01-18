package dataview.tablemodel;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class KeyEventTableModel extends DefaultTableModel
{	
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = { "Event No.", "Event Type", "Distance(m)", "Loss(dB)", "Reflectance(dB)",
			"Slope(dB/km)", "Rel.Dist.(m)", "Total loss(OTDR)(dB)"};
//	private static String[] columnNames = {
//			"编号", "事件类型", "位置(m)", "损耗(dB)", "反射(dB)", 
//			"衰减(dB/km)", "相对上一事件距离(m)","累损(OTDR)(dB)",
//			"" };
			
	private static Vector<String> columnNameList = new Vector<String>(Arrays.asList(columnNames));
	
	@Override
	 public boolean isCellEditable(int row, int column)
	{
	        return false;
	 }
	
	public KeyEventTableModel()
	{
		super(columnNameList, 0);
	}
	
	public static void hideTableColumn (JTable table , int column)
	{
		TableColumnModel tcm = table.getColumnModel();
		TableColumn tc = tcm.getColumn(column);
		tcm.removeColumn(tc);
	}
	
}
