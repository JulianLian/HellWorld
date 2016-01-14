package domain;

import java.io.FileInputStream;
import java.util.Properties;

public class ProductInfo
{
	public static final String TITLE = "XXX光缆故障检测系统";
	public static final String PROPERTY_FILE = "ProductName.properites";

	public static String getProductName ()
	{
		Properties props = new Properties();
		String productName = null;
		try
		{
			props.load(new FileInputStream(PROPERTY_FILE));
			productName = props.getProperty("title");
			String strReturn = new String(productName.getBytes("ISO-8859-1"), "GBK");
			productName = strReturn;
		}
		catch (Exception e)
		{
			productName = ProductInfo.TITLE;
		}
		return productName;
	}
}
