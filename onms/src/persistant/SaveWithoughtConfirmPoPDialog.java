/*
 *
 *@author 杨安印
 *
 *这个对话框要填入的数据是用户对要保存图形
 *的信息的认定
 *
 *
 *
 *
 */
package persistant;

import java.io.File;
import java.io.FileOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import domain.BusinessConst;
import domain.SerialDataFromToFile;
import main.Md711MainFrame;

public class SaveWithoughtConfirmPoPDialog
{
	public static String getCableType ()
	{
		return "";
		
	}
	
	public static void setCableType (String a)
	{
	
	}
	
	// *****************************
	public static String getCableLength ()
	{
		return "";
		
	}
	
	public static void setCableLength (String a)
	{
	
	}
	
	// ***************************
	public static String getDsDepth ()
	{
		return "";
		
	}
	
	public static void setDsDepth (String a)
	{
	
	}
	
	// ***************************
	public static String getFsDate ()
	{
		return "";
		
	}
	
	public static void setFsDate (String a)
	{
	
	}
	
	// ***************************
	public static String getWrongType ()
	{
		return "";
		
	}
	
	public static void setWrongType (String a)
	{
	
	}
	
	// ****************************
	public static String getWrongDistance ()
	{
		return "";
		
	}
	
	public static void setWrongDistance (String a)
	{
	
	}
	
	// ***************************
	public static String getTestClerk ()
	{
		return "";
		
	}
	
	public static void setTestClerk (String a)
	{
	
	}
	
	// ******************
	public static String getTestDate ()
	{
		return "";
		
	}
	
	public static void setTestDate (String a)
	{
	
	}
	
	// ******************
	public static String getNote ()
	{
		return "";
		
	}
	
	public static void setNote (String a)
	{
	
	}
	
	public void clearAll ()
	{
	
	}
	
	// *************************************************事件处理
	public static void save (Md711MainFrame mf)
	{
		String tempCapleType = new String();
		String tempCableLength = new String();
		String tempDsDepth = new String();
		String tempFsDate = new String();
		String tempWrongType = new String();
		String tempWrongDistance = new String();
		String tempTestClerk = new String();
		String tempTestDate = new String();
		String tempNote = new String();
		
		// 下面就是打开按钮
		JFileChooser jf = new JFileChooser();
		jf.setCurrentDirectory(new File("data"));
		int returnVal = jf.showSaveDialog(mf);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			
			SerialDataFromToFile one = null;
			if (mf.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT)
			{
				one = new SerialDataFromToFile(
						InventoryData.getDataFromPortImmutable(), tempCapleType,
						tempCableLength, tempDsDepth, tempFsDate, tempWrongType,
						tempWrongDistance, tempTestClerk, tempTestDate, tempNote);
			}
			else if (mf.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				one = new SerialDataFromToFile(
						InventoryData.getCanTransformedDataFromFile(), tempCapleType,
						tempCableLength, tempDsDepth, tempFsDate, tempWrongType,
						tempWrongDistance, tempTestClerk, tempTestDate, tempNote);
			}
			try
			{
				
				File file = jf.getSelectedFile();
				FileOutputStream f = new FileOutputStream(file);
				one.writeToFile(f);
				JOptionPane.showMessageDialog(mf, "保存成功", "保存结果",
						JOptionPane.INFORMATION_MESSAGE);
			}
			catch (Exception ddd)
			{
				javax.swing.JOptionPane.showMessageDialog(null, "文件保存发生错误");
				System.err.println("文件保存发生错误");
				
			}			
		}
	}
}
