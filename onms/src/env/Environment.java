package env;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Environment
{
	private static final String PEERIP = "IP";
	private static final String LISTEN_PORT = "ListenPort";
	private static final String TIMEOUT = "Timeout";
	private static final String PARAM_FILE_PATH = "communicationParam.txt";
	
	public static String	peerIP = "127.0.0.1";
	public static String	listenPort= "8080";
	public static String	timeOut= "100";
	
	static
	{
		readEnv();
	}
	
	public static void saveCommunicationParam(String peerIPParam, String listenPortParam, String timeoutParam)
	{
		String[] params = new String[3];
		params[0] = PEERIP+"="+peerIPParam;
		params[1] = LISTEN_PORT+"="+listenPortParam;
		params[2] = TIMEOUT+"="+timeoutParam;	
		peerIP =peerIPParam;
		listenPort =listenPortParam;
		timeOut = timeoutParam;
		writeMap(params, PARAM_FILE_PATH);
	}
	
	private static void writeMap(String[] paramPairs,  String filePath)
	{
		FileWriter fw = null;
		BufferedWriter bw = null;
		try
		{				
			 fw = new FileWriter(filePath); 	
			 bw = new BufferedWriter(fw);				
			for(String paramPair : paramPairs)				
			{					
				 bw.write(paramPair);
				 bw.newLine();					
			}
			 bw.flush();					 			
		}
		catch (IOException e)
		{
			MDLogger.INS.error(e.getMessage());
		}
		finally
		{			
			try
			{
				if(bw != null)
					fw.close();
				if(fw != null)
					fw.close();
			}
			catch (IOException e)
			{								
				MDLogger.INS.error(e.getMessage());
			}
		}
	}
	
	public static void readEnv()
	{
		FileReader fr = null;
		BufferedReader br = null;
		try
		{
			if(!new File(PARAM_FILE_PATH).exists())
			{
				return ; 
			}
			fr = new FileReader(PARAM_FILE_PATH);
			br = new BufferedReader(fr);
			String readoneline;
			while ((readoneline = br.readLine()) != null)
			{
				if(readoneline.startsWith(PEERIP+"="))
				{
						peerIP = readoneline.substring((PEERIP+"=").length());
				}
				else if(readoneline.startsWith(LISTEN_PORT+"="))
				{
						listenPort = readoneline.substring((LISTEN_PORT+"=").length());
				}
				else if(readoneline.startsWith(TIMEOUT+"="))
				{
					timeOut = readoneline.substring((TIMEOUT+"=").length());
				}
			}			
		}
		catch (IOException e)
		{
			MDLogger.INS.error(e.getMessage());
		}
		finally
		{					
			try
				{
					if(br != null)
						br.close();
					if(fr != null)
						fr.close();
				}
				catch (IOException e)
				{
					MDLogger.INS.error(e.getMessage());
				}
		}
	}
}
