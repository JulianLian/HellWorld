package communation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import datastruct.SerializableData;
import main.Md711MainFrame;
import persistant.WindowControlEnv;

public class FileDataGetter implements IDataGetter
{
	private Md711MainFrame mainFrame;
	
	public FileDataGetter(Md711MainFrame mainFrame)
	{
		super();
		this.mainFrame = mainFrame;
	}	

	@Override
	public List<Double> getWaveData (Map<String, String> permittedVal)
	{
		List<Double> waveData = null;
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
					waveData = SerializableData.readFromFile(f, mainFrame);
				}
				catch (Exception ee)
				{					
					JOptionPane.showMessageDialog(mainFrame, "文件读入时发生错误...", "错误",
							JOptionPane.ERROR_MESSAGE);					
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
		return waveData;
	}

	@Override
	public List<String> getEventData (Map<String, String> permittedVal)
	{
		// TODO Auto-generated method stub
		return null;
	}
}
