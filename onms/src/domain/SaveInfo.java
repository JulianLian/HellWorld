package domain;

import datastruct.SerialDataFromToFile;

public class SaveInfo
{
	// 下面是有关文件中调入得波形得信息，即用户在存储时输入得信息

	private static String cableType = "";

	private static String cableLength = "";

	private static String dsDepth = "";

	private static String fsDate = "";

	private static String wrongType = "";

	private static String wrongDistance = "";

	private static String testClerk = "";

	private static String testDate = "";

	private static String note = "";

	// ****************************************** *********************
	// *****************************************
	// 设定获取文件信息**********************
	// ***************************************** **********************
	public static String getCableType ()
	{
		return cableType;

	}

	public static void setCableType (String a)
	{
		cableType = a;
	}

	// *****************************
	public static String getCableLength ()
	{
		return cableLength;

	}

	public static void setCableLength (String a)
	{
		cableLength = a;
	}

	// ***************************
	public static String getDsDepth ()
	{
		return dsDepth;

	}

	public static void setDsDepth (String a)
	{
		dsDepth = a;
	}

	// ***************************
	public static String getFsDate ()
	{
		return fsDate;

	}

	public static void setFsDate (String a)
	{
		fsDate = a;
	}

	// ***************************
	public static String getWrongType ()
	{
		return wrongType;

	}

	public static void setWrongType (String a)
	{
		wrongType = a;
	}

	// ****************************
	public static String getWrongDistance ()
	{
		return wrongDistance;

	}

	public static void setWrongDistance (String a)
	{
		wrongDistance = a;
	}

	// ***************************
	public static String getTestClerk ()
	{
		return testClerk;

	}

	public static void setTestClerk (String a)
	{
		testClerk = a;
	}

	// ******************
	public static String getTestDate ()
	{
		return testDate;

	}

	public static void setTestDate (String a)
	{
		testDate = a;
	}

	// ******************
	public static String getNote ()
	{
		return note;

	}

	public static void setNote (String a)
	{
		note = a;
	}

//	public static void restoreSaveInfo (SerialDataFromToFile s)
//	{
//		SaveInfo.setCableLength(s.getCableLength());
//		SaveInfo.setCableType(s.getCableType());
//		SaveInfo.setDsDepth(s.getDsDepth());
//		SaveInfo.setFsDate(s.getFsDate());
//		SaveInfo.setWrongType(s.getWrongType());
//		SaveInfo.setWrongDistance(s.getWrongDistance());
//		SaveInfo.setTestClerk(s.getTestClerk());
//		SaveInfo.setTestDate(s.getTestDate());
//		SaveInfo.setNote(s.getNote());
//	}
}
