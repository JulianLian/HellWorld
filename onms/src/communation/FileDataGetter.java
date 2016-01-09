package communation;

import domain.SerialDataFromToFile;
import main.Md711MainFrame;
import persistant.WindowControlEnv;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileDataGetter implements IDataGetter
{
		private Md711MainFrame mainFrame;

		public FileDataGetter(Md711MainFrame mainFrame)
		{
			super();
			this.mainFrame = mainFrame;
		}

		@Override
		public boolean startFetchData()
		{
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(mainFrame);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				if (file != null)
				{
					FileInputStream f = null;
					try
					{
							f = new FileInputStream(file);
							SerialDataFromToFile.readFromFile(f);
							mainFrame.getGraphControllerpanel().getCurSelectionPanel().selectFileDataLine();
							WindowControlEnv.setRepaintForFileInfoCome(true);
							mainFrame.getGraphControllerpanel().setStateWhenOpenFile();
							mainFrame.showFileGraph();
							return true;
					}
					catch (Exception ee)
					{
							mainFrame.getGraphControllerpanel().getMoveAndAmplyPanel().setStepEnable(false);
							JOptionPane.showMessageDialog(mainFrame, "文件读入时发生错误...", "错误", JOptionPane.ERROR_MESSAGE);
							return false;
					}
					finally
					{
							try
							{
								f.close();
							}
							catch (IOException e)
							{
								JOptionPane.showMessageDialog(mainFrame, "文件读入时发生错误...", "错误",
										JOptionPane.ERROR_MESSAGE);
							}
					}
				}
			}
			return false;
		}

		@Override
		public boolean stopFetchData()
		{
			return true;
		}
}
