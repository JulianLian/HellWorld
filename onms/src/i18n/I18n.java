package i18n;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import env.MDLogger;

public class I18n
{
	public static final String PROPERTY_FILE = "international.properties";
//	public static final String PROPERTY_FILE = "international.properties";
	public static final I18n INS = new I18n();
	private Properties props = new Properties();
	private I18n()
	{
		try
		{
			props.load(new FileInputStream(PROPERTY_FILE));
		}
		catch (IOException e)
		{
			props = null;
			MDLogger.INS.error(e.getMessage());
		}
	}
	
	public String getI18Str(String key)
	{
		if(props != null)
		{
			String s = props.getProperty(key);
			try
			{
				return s == null ? "" : new String(s.getBytes("ISO-8859-1"), "GBK");
			}
			catch (UnsupportedEncodingException e)
			{
				MDLogger.INS.error(e.getMessage());
				return "";
			}
		}
		return "";
	}
}
