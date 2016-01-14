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

import datastruct.SerialDataFromToFile;
import domain.BusinessConst;
import main.Md711MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;

public class SaveWithoughtConfirmPoPDialog
{
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
