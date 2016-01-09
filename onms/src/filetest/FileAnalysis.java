package filetest;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileAnalysis
{
		public static List<Double> readline(String file)
		{
			List<Double> doubleVal = new ArrayList<Double>();
			BufferedReader br = null;
			try
			{
				FileReader fr = new FileReader(file);
				br = new BufferedReader(fr);
				String readoneline;
				while ((readoneline = br.readLine()) != null)
				{
					String[] s = readoneline.split(",");
					if (s != null && s.length == 2 && isNumeric(s[0]) && isNumeric(s[1]))
					{
							doubleVal.add(Double.parseDouble(s[0]));
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			return doubleVal;
		}

		public static boolean isNumeric(String str)
		{
			for (int i = str.length(); --i >= 0;)
			{
				char oneChar = str.charAt(i);
				if (oneChar != '.' && oneChar != '-' && !Character.isDigit(oneChar))
				{
					return false;
				}
			}
			return true;
		}
}
