package persistant;

public class WindowControlEnv
{
	// 这里面分别存放两条线各自的步长
	private static int stepValForportData = 1;
	private static int stepValForFileData = 1;

	// *************************端口里面提取处理的数据采用的步长
	public static int getStepValForPortData ()
	{
		return stepValForportData;
	}

	public static void setStepValForPortData (int a)
	{
		stepValForportData = a;
	}

	// ***********************文件里面提取处理的数据采用的步长
	public static int getStepValForFileData ()
	{
		return stepValForFileData;
	}

	public static void setStepValForFileData (int a)
	{
		stepValForFileData = a;
	}

	public static void toDefaultParam ()
	{
		stepValForportData = 1;
		stepValForFileData = 1;
		fileStepNotClicked = true;
		portStepNotClicked = true;
		repaintForPortInfoCome = false;
		repaintForFileInfoCome = false;
		mediaSpeed = 160;
	}

	// *******************************************************************************
	// 用户点击了步长按钮
	private static boolean fileStepNotClicked = true;
	private static boolean portStepNotClicked = true;

	private static boolean repaintForPortInfoCome = false;
	private static boolean repaintForFileInfoCome = false;

	public static void setFileStepNotClicked (boolean b)
	{

		fileStepNotClicked = b;

	}

	public static boolean getFileStepNotClicked ()
	{

		return fileStepNotClicked;

	}

	public static void setPortStepNotClicked (boolean b)
	{

		portStepNotClicked = b;

	}

	public static boolean getPortStepNotClicked ()
	{

		return portStepNotClicked;

	}

	public static boolean getRepaintForPortInfoCome ()
	{
		return repaintForPortInfoCome;

	}

	public static void setRepaintForPortInfoCome (boolean a)
	{
		repaintForPortInfoCome = a;
	}

	// *********************repaintForFileInfoCome=false;
	public static boolean getRepaintForFileInfoCome ()
	{
		return repaintForFileInfoCome;

	}

	public static void setRepaintForFileInfoCome (boolean a)
	{
		repaintForFileInfoCome = a;
	}

	// *********************************************************************************
	// 介质速度
	private static double mediaSpeed = 160;

	// **************************用户选择了什么样的测量速度
	public static double getMediaSpeed ()
	{

		return mediaSpeed;

	}

	public static void setMediaSpeed (double s)
	{
		mediaSpeed = s;
	}

	// *********************************************************************************
	private static boolean portDataHaveSaved = false;

	// portDataHaveSaved
	public static boolean getPortDataHaveSaved ()
	{
		return portDataHaveSaved;

	}

	public static void setPortDataHaveSaved (boolean a)
	{
		portDataHaveSaved = a;
	}
}
