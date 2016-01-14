package main;

/*
 *@author: 杨安印
 *@data:   20005-8-29
 *
 *本程序意在打印机中输出图形，
 *整个界面是上方是图形，下方是详细资料
 *
 */
import domain.BusinessConst;
import domain.SaveInfo;
import persistant.InventoryData;
import persistant.PoPDialog;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.print.PageFormat;
import java.awt.print.Printable;

public class PrintGraph implements Printable
{
	private int lineDis; // 行间隔
	private int colDis; // 列间隔
	private Md711MainFrame mainFrame;

	public PrintGraph(Md711MainFrame mainFrame)
	{
		super();
		this.mainFrame = mainFrame;
	}

	// **********************************************************主要成分
	public int print (Graphics g , PageFormat format , int pagenum)
	{
		colDis = ((int) format.getImageableWidth() - 20) / 4;
		lineDis = ((int) format.getImageableHeight()) / 12;

		Graphics2D d = (Graphics2D) g;

		d.translate(format.getImageableX(), format.getImageableY());
		d.drawString("电缆故障测试报告", (int) format.getImageableWidth() / 2 - 20, 30);

		if (pagenum > 0)
			return NO_SUCH_PAGE;

		// 首先覆盖掉原先的图形
		d.setColor(Color.white);
		d.fillRect((int) format.getImageableX(), (int) format.getImageableY(), (int) format.getImageableWidth(),
				(int) format.getImageableHeight());

		// 设置字体颜色
		d.setColor(Color.black);

		// 原点平移到1/4,画出两条坐标轴

		d.translate(0, (int) ((format.getImageableHeight()) / 4 + 60));
		d.drawLine(10, 30, (int) format.getImageableWidth() - 10, 30);
		d.drawLine(10, -(int) ((format.getImageableHeight()) / 4), 10, 30);

		int screenWidth = mainFrame.getGraph().getWidth();
		int screenHeight = mainFrame.getGraph().getHeight();

		// **************************文件数据打印
		if (mainFrame.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.FILESELECT
				&& InventoryData.getCanTransformedDataFromFile().size() != 0)
		{
			GeneralPath path = new GeneralPath();

			int showGraphLen = screenWidth;
			double interval = (((Double) (InventoryData.getXDataFromFile().get(1))).doubleValue()
					- ((Double) (InventoryData.getXDataFromFile().get(0))).doubleValue());
			int length = (int) (showGraphLen / interval) + 1;

			double yScale = screenHeight / format.getImageableHeight();
			float xData[] = new float[length];
			float yData[] = new float[length];

			for (int i = 0; i < length; i++)
			{

				xData[i] = ((float) (format.getImageableWidth() - 20) / (length - 1)) * i + 10;

				yData[i] = (-1) * (float) (((Double) (InventoryData.getCanTransformedDataFromFile()
						.get(i))).doubleValue() / yScale);

				if (i == 0)
					path.moveTo(xData[i], ((yData[i]) - 5) / 4);
				else
					path.lineTo(xData[i], ((yData[i]) - 5) / 4);

			}
			d.draw(path);
			// ************************文件信息打印

			d.translate(10, (int) format.getImageableHeight() / 2 - (int) format.getImageableHeight() / 4
					- 60);

			// 5条竖直的线
			d.drawLine(0, 0, 0, (int) (format.getImageableHeight() / 4) + 2 * lineDis);
			d.drawLine(colDis, 0, colDis, (int) (format.getImageableHeight() / 4) + 2 * lineDis);
			d.drawLine(colDis * 2, 0, colDis * 2, (int) (format.getImageableHeight() / 4) + lineDis);
			d.drawLine(colDis * 3, 0, colDis * 3, (int) (format.getImageableHeight() / 4) + lineDis);
			d.drawLine(colDis * 4, 0, colDis * 4, (int) (format.getImageableHeight() / 4) + 2 * lineDis);

			// 4条水平线
			d.drawLine(0, 0, (int) format.getImageableWidth() - 20, 0);
			d.drawLine(0, lineDis, (int) format.getImageableWidth() - 20, lineDis);
			d.drawLine(0, lineDis * 2, (int) format.getImageableWidth() - 20, lineDis * 2);
			d.drawLine(0, lineDis * 3, (int) format.getImageableWidth() - 20, lineDis * 3);
			d.drawLine(0, lineDis * 4, (int) format.getImageableWidth() - 20, lineDis * 4);
			d.drawLine(0, lineDis * 5, (int) format.getImageableWidth() - 20, lineDis * 5);
			// 现在是里面的内容
			d.drawString("电缆型号", 0 + 10, lineDis - 2);
			d.drawString(SaveInfo.getCableType(), colDis + 2, lineDis - 2);
			d.drawString("故障性质", colDis * 2 + 10, lineDis - 2);
			d.drawString(SaveInfo.getWrongType(), colDis * 3 + 2, lineDis - 2);

			d.drawString("电缆长度(米)", 0 + 10, lineDis * 2 - 2);
			d.drawString(SaveInfo.getCableLength(), colDis + 2, lineDis * 2 - 2);
			d.drawString("故障距离(米)", colDis * 2 + 10, lineDis * 2 - 2);
			d.drawString(SaveInfo.getWrongDistance(), colDis * 3 + 2, lineDis * 2 - 2);

			d.drawString("敷设深度(米)", 0 + 10, lineDis * 3 - 2);
			d.drawString(SaveInfo.getDsDepth(), colDis + 2, lineDis * 3 - 2);
			d.drawString("测试日期", colDis * 2 + 10, lineDis * 3 - 2);
			d.drawString(SaveInfo.getTestDate(), colDis * 3 + 2, lineDis * 3 - 2);

			d.drawString("敷设日期", 0 + 10, lineDis * 4 - 2);
			d.drawString(SaveInfo.getFsDate(), colDis + 2, lineDis * 4 - 2);
			d.drawString("测试人员", colDis * 2 + 10, lineDis * 4 - 2);
			d.drawString(SaveInfo.getTestClerk(), colDis * 3 + 2, lineDis * 4 - 2);

			d.drawString("备注", 0 + 10, lineDis * 5 - 2);
			d.drawString(SaveInfo.getNote(), colDis + 2, lineDis * 5 - 2);
		}
		// **************************端口数据打印
		else if (mainFrame.getGraphControllerpanel().getCurSelectedCurve() == BusinessConst.PORTSELECT
				&& InventoryData.getXDataFromPortLength() != 0)
		{

			GeneralPath path = new GeneralPath();

			// double
			// interval=(((Double)(InventoryData.getXDataFromFile().get(1))).doubleValue()-((Double)(InventoryData.getXDataFromFile().get(0))).doubleValue());
			double interval = (((Double) (InventoryData.getXDataFromPort().get(1))).doubleValue()
					- ((Double) (InventoryData.getXDataFromPort().get(0))).doubleValue());

			int length = (int) (screenWidth / interval) + 1;

			double yScale = screenHeight / format.getImageableHeight();
			float xData[] = new float[length];
			float yData[] = new float[length];

			for (int i = 0; i < length; i++)
			{

				xData[i] = ((float) (format.getImageableWidth() - 20) / (length - 1)) * i + 10;

				yData[i] = (-1) * (float) (((Double) (InventoryData.getCanTransformedDataFromPort()
						.get(i))).doubleValue() / yScale);

				if (i == 0)
					path.moveTo(xData[i], ((yData[i]) - 5) / 4);
				else
					path.lineTo(xData[i], ((yData[i]) - 5) / 4);

			}

			d.draw(path);

			// ************************文件信息打印

			d.translate(10, (int) format.getImageableHeight() / 2 - (int) format.getImageableHeight() / 4
					- 60);

			// 5条竖直的线
			d.drawLine(0, 0, 0, (int) (format.getImageableHeight() / 4) + 2 * lineDis);
			d.drawLine(colDis, 0, colDis, (int) (format.getImageableHeight() / 4) + 2 * lineDis);
			d.drawLine(colDis * 2, 0, colDis * 2, (int) (format.getImageableHeight() / 4) + lineDis);
			d.drawLine(colDis * 3, 0, colDis * 3, (int) (format.getImageableHeight() / 4) + lineDis);
			d.drawLine(colDis * 4, 0, colDis * 4, (int) (format.getImageableHeight() / 4) + 2 * lineDis);

			// 4条水平线
			d.drawLine(0, 0, (int) format.getImageableWidth() - 20, 0);
			d.drawLine(0, lineDis, (int) format.getImageableWidth() - 20, lineDis);
			d.drawLine(0, lineDis * 2, (int) format.getImageableWidth() - 20, lineDis * 2);
			d.drawLine(0, lineDis * 3, (int) format.getImageableWidth() - 20, lineDis * 3);
			d.drawLine(0, lineDis * 4, (int) format.getImageableWidth() - 20, lineDis * 4);
			d.drawLine(0, lineDis * 5, (int) format.getImageableWidth() - 20, lineDis * 5);
			// 现在是里面的内容
			d.drawString("电缆型号", 0 + 10, lineDis - 2);
			d.drawString(PoPDialog.getCableType(), colDis + 2, lineDis - 2);
			d.drawString("故障性质", colDis * 2 + 10, lineDis - 2);
			d.drawString(PoPDialog.getWrongType(), colDis * 3 + 2, lineDis - 2);

			d.drawString("电缆长度(米)", 0 + 10, lineDis * 2 - 2);
			d.drawString(PoPDialog.getCableLength(), colDis + 2, lineDis * 2 - 2);
			d.drawString("故障距离(米)", colDis * 2 + 10, lineDis * 2 - 2);
			d.drawString(PoPDialog.getWrongDistance(), colDis * 3 + 2, lineDis * 2 - 2);

			d.drawString("敷设深度(米)", 0 + 10, lineDis * 3 - 2);
			d.drawString(PoPDialog.getDsDepth(), colDis + 2, lineDis * 3 - 2);
			d.drawString("测试日期", colDis * 2 + 10, lineDis * 3 - 2);
			d.drawString(PoPDialog.getTestDate(), colDis * 3 + 2, lineDis * 3 - 2);

			d.drawString("敷设日期", 0 + 10, lineDis * 4 - 2);
			d.drawString(PoPDialog.getFsDate(), colDis + 2, lineDis * 4 - 2);
			d.drawString("测试人员", colDis * 2 + 10, lineDis * 4 - 2);
			d.drawString(PoPDialog.getTestClerk(), colDis * 3 + 2, lineDis * 4 - 2);

			d.drawString("备注", 0 + 10, lineDis * 5 - 2);
			d.drawString(PoPDialog.getNote(), colDis + 2, lineDis * 5 - 2);
		}

		return PAGE_EXISTS;
	}

}
