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

import communation.WaveDataSuffixFilter;
import datastruct.SerialDataFromToFile;
import domain.BusinessConst;
import env.MDLogger;
import main.Md711MainFrame;

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
		WaveDataSuffixFilter fileFilter = new WaveDataSuffixFilter();		
		jf.setFileFilter(fileFilter);
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
						tempWrongDistance, tempTestClerk, tempTestDate, tempNote,
						mf.getEventPanel().getkeyPointPanel().getEventData(),
						mf.getEventPanel().getkeyPointPanel().getSelectedDevParam());
			}
			else if (mf.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT)
			{
				one = new SerialDataFromToFile(
						InventoryData.getCanTransformedDataFromFile(), tempCapleType,
						tempCableLength, tempDsDepth, tempFsDate, tempWrongType,
						tempWrongDistance, tempTestClerk, tempTestDate, tempNote,
						mf.getEventPanel().getkeyPointPanel().getEventData(),
						mf.getEventPanel().getkeyPointPanel().getSelectedDevParam());
			}
			saveFile(mf, jf, one);			
		}
	}

	private static void saveFile (Md711MainFrame mf , JFileChooser jf , SerialDataFromToFile one)
	{
			try
			{
				File file = jf.getSelectedFile();
			String filepath=file.getPath();			        
			String fileName = file.getName();
			FileOutputStream f = null;
			 if(!fileName.endsWith(".wave"))
			 {
				 fileName += ".wave";
				 int nindex1=filepath.lastIndexOf("\\");
			         String filepath1=filepath.substring(0,nindex1+1);				         
			         f= new FileOutputStream(filepath1+fileName);
			 }
			 else
			 {
				 f = new FileOutputStream(file);
			 }				
				one.writeToFile(f);
				JOptionPane.showMessageDialog(mf, "保存成功", "保存结果",
						JOptionPane.INFORMATION_MESSAGE);
//				f.close();
			
//				File file = jf.getSelectedFile();
//				FileOutputStream f = new FileOutputStream(file);
//				one.writeToFile(f);
//				JOptionPane.showMessageDialog(mf, "保存成功", "保存结果",
//						JOptionPane.INFORMATION_MESSAGE);
			}
			catch (Exception e)
			{
				MDLogger.INS.error(e.getMessage());
				javax.swing.JOptionPane.showMessageDialog(null, "文件保存发生错误");
			}			
		}
	}
