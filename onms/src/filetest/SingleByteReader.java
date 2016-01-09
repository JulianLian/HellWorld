package filetest;

import java.io.FileReader;
import java.io.IOException;

public class SingleByteReader
{
		public static final String fileName = "measure_6_511593632_Agilent.txt";

		public static void readfile(String file)
		{
			try
			{
				FileReader fr = new FileReader(file);
				int ch = 0;
				while ((ch = fr.read()) != -1)
				{
					System.out.println((char) ch);
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		public static void main(String[] args)
		{
			readfile(fileName);
		}

}
