package communation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import datastruct.SerializableData;
import env.MDLogger;
import main.Md711MainFrame;

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
		fc.setFileFilter(new WaveDataSuffixFilter());
		int returnVal = fc.showOpenDialog(mainFrame);
		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			File file = fc.getSelectedFile();
			waveData = readFileData(file, mainFrame);
		}
		return waveData;
	}

	public static List<Double> readFileData (File file, Md711MainFrame fatherComponent)
	{
		List<Double> waveData = null;
			if (file != null)
			{
				FileInputStream f = null;
				try
				{
					f = new FileInputStream(file);
				        waveData = SerializableData.readFromFile(f, fatherComponent);
				}
				catch (Exception ee)
				{					
					MDLogger.INS.error(ee.getMessage());
					JOptionPane.showMessageDialog(fatherComponent, "文件读入时发生错误...", "错误",
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
						MDLogger.INS.error(e.getMessage());
						JOptionPane.showMessageDialog(fatherComponent, "文件读入时发生错误...", "错误",
								JOptionPane.ERROR_MESSAGE);
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
