package dataview;

import javax.swing.ImageIcon;

public class Constant
{
	public static final String BASE_IMG_PATH = "../icon/";
	
	public static ImageIcon createImageIcon (String path)
	{
		java.net.URL imgURL = Constant.class.getResource(dataview.Constant.BASE_IMG_PATH + path);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL);
		}
		else
		{
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
}
