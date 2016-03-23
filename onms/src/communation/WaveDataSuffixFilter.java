package communation;

import javax.swing.filechooser.FileFilter;

public class WaveDataSuffixFilter extends FileFilter
{
	public boolean accept (java.io.File f)
	{
		if (f.isDirectory())
			return true;
		return f.getName().endsWith(".wave"); 
	}
	
	public String getDescription ()
	{
		return ".wave";
	}
	
}
