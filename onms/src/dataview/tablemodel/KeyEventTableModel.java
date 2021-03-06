package dataview.tablemodel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.util.Arrays;
import java.util.Vector;

	public class KeyEventTableModel extends DefaultTableModel
{
	private static final long serialVersionUID = 1L;
	private static String[] columnNames = {
//			I18n.INS.getI18Str("NO"), I18n.INS.getI18Str("EventType"),
//			I18n.INS.getI18Str("Position"), I18n.INS.getI18Str("loss"),
//			I18n.INS.getI18Str("reflect"), I18n.INS.getI18Str("slope"),
//			I18n.INS.getI18Str("distanceToPreEvent"),
//			I18n.INS.getI18Str("lossTotal"),
//			""
			"编号", "事件类型", "位置(m)", "损耗(dB)", "反射(dB)",
			"衰减(dB/km)", "相对上一事件距离(m)","累损(OTDR)(dB)",
			""
			};
			
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
