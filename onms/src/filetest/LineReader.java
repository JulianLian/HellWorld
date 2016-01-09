package filetest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

public class LineReader
{
	public static void readline(String file, String writerfile)
	{
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			// FileWriter fw = new FileWriter(writerfile); //
			// 写文件操作，把得到的file对应的文件中内容写入，writerfile中去。
			// BufferedWriter bw = new BufferedWriter(fw);
			String readoneline;
			while ((readoneline = br.readLine()) != null)
			{
				;
				// bw.write(readoneline);
				// bw.newLine();
				System.out.println(readoneline);
			}
			// bw.flush();
			br.close();
			// bw.close();
			br.close();
			// fw.close();
			fr.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public static void writeMap(Map<String, String> paramPair,  String filePath)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{
			fw = new FileWriter(filePath);
			bw = new BufferedWriter(fw);
			for(Entry<String, String> paramEntry :  paramPair.entrySet())
			{
				bw.write(paramEntry.getKey()+"="+paramEntry.getValue());
				bw.newLine();
			}
			bw.flush();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(bw != null)
			{
				try
				{
					bw.close();
				}
				catch (IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fw != null)
			{
				try
				{
					fw.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args)
	{
		readline(filetest.SingleByteReader.fileName, "e:\\aa.txt");
	}
}
